package de.bvb.mydemo.util.lrcCache;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import de.bvb.mydemo.util.IOUtils;
import de.bvb.mydemo.util.lrcCache.DiskLruCache.Editor;

public class ImageLoader {

    protected static final String TAG = "ImageLoader";

    private final long DISK_CACHE_SIZE = 1024 * 1024 * 50;

    private Context context;
    private LruCache<String, Bitmap> memoryCache;
    private boolean isDiskLruCacheCreated = false;
    private DiskLruCache diskLruCache;
    private int diskCacheIndex;
    private ImageResizer imageResizer =new ImageResizer();

    private ImageLoader(Context context) {
        this.context = context;
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 16);
        memoryCache = new LruCache<String, Bitmap>(memoryCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
        // 根据类存卡的状态获取缓存目录:/storage/emulated/0/Android/data/com.example.imageloader/cache/bitmap
        File diskCacheDirectory = new File((Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? context.getExternalCacheDir()
                : context.getCacheDir()).getPath()
                + File.separator + "bitmap");
        if (!diskCacheDirectory.exists()) {
            diskCacheDirectory.mkdirs();
        }
        // 获取可用空间大小
        StatFs statFs = new StatFs(diskCacheDirectory.getPath());
        long usableSpace = (long) statFs.getBlockSize() * (long) statFs.getAvailableBlocks();
        // 创建磁盘缓存
        if (usableSpace > DISK_CACHE_SIZE) {
            try {
                diskLruCache = DiskLruCache.open(diskCacheDirectory, 1, 1, DISK_CACHE_SIZE);
                isDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
    }

    public void bindBitmap(String url, ImageView iv) {
        bindBitmap(url, iv, 0, 0);
    }

    private final int MESSAGE_RESULT = 0x1;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView iv = result.iv;
            // iv.setImageBitmap(result.bitmap);
            String url = (String) iv.getTag();
            if (url.equals(result.url)) {
                iv.setImageBitmap(result.bitmap);
            }
        };
    };

    public void bindBitmap(final String url, final ImageView iv, final int width, final int height) {
        iv.setTag(url);
        Bitmap bitmap = loadBitmapFromMemoryCache(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
        } else {
            THREAD_POOL_EXECTOR.execute(new Runnable() {

                @Override
                public void run() {
                    Bitmap bitmap = loadBitmap(url, width, height);
                    if (bitmap != null) {
                        LoaderResult loaderResult = new LoaderResult(iv, url, bitmap);
                        handler.obtainMessage(MESSAGE_RESULT, loaderResult).sendToTarget();
                        Log.e(TAG, Thread.currentThread().getName());
                    }
                }

            });
        }
    }

    private Bitmap loadBitmap(String url, int width, int height) {
        Bitmap bitmap = loadBitmapFromMemoryCache(url);
        if (bitmap != null) {
            return bitmap;
        }
        try {
            bitmap = loadBitmapFromDiskCache(url, width, height);
            if (bitmap != null) {
                return bitmap;
            }
            bitmap = loadBitmapFromHttp(url, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null && !isDiskLruCacheCreated) {

        }
        return bitmap;
    }

    private Bitmap loadBitmapFromMemoryCache(String url) {
        return memoryCache.get(getHashKeyFromUrl(url));
    }

    private Bitmap loadBitmapFromDiskCache(String url, int width, int height) throws IOException {
        if (diskLruCache == null) {
            return null;
        }
        Bitmap bitmap = null;
        String key = getHashKeyFromUrl(url);
        DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream in = (FileInputStream) snapshot.getInputStream(diskCacheIndex);
            FileDescriptor fileDescriptor = in.getFD();
            bitmap = imageResizer.decodeSampledBitmapFromFileDescriptor(fileDescriptor, width, height);
            if (bitmap != null) {
                addBitmapToMemoryCache(key, bitmap);
            }
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromHttp(String url, int width, int height) throws IOException {
        String key = getHashKeyFromUrl(url);
        Editor edit = diskLruCache.edit(key);
        if (edit != null) {
            OutputStream outputStream = edit.newOutputStream(diskCacheIndex);
            if (IOUtils.parseUrl2Stream(url, outputStream)) {
                edit.commit();
            } else {
                edit.abort();
            }
            diskLruCache.flush();
        }
        return loadBitmapFromDiskCache(url, width, height);
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (memoryCache.get(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    private String getHashKeyFromUrl(String url) {
        String cacheKey = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheKey = byteToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            sb.append(hex.length() == 1 ? "0" : "").append(hex);
        }
        return sb.toString();
    }

    private class LoaderResult {
        private ImageView iv;
        private String url;
        private Bitmap bitmap;

        public LoaderResult(ImageView iv, String url, Bitmap bitmap) {
            this.iv = iv;
            this.url = url;
            this.bitmap = bitmap;
        }

    }

    // 构建线程池 /////////////////////////////////////////////////////////////////////////////////////////
    private static final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger _id = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoaderThreadName========>" + _id.getAndIncrement());
        } 
    };

    private static final int cpu_count = Runtime.getRuntime().availableProcessors();
    private static final int corePoolSize = cpu_count + 1;
    private static final int maximumPoolSize = cpu_count * 2 + 1;
    private static final long keepAliveTime = 10L;
    private static final Executor THREAD_POOL_EXECTOR = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(), threadFactory);
    // 构建线程池 /////////////////////////////////////////////////////////////////////////////////////////
}
