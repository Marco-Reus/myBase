package de.bvb.mydemo.util.lrcCache;

import java.io.FileDescriptor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ImageResizer {

    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fileDescriptor, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    private int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (reqWidth > 0 || reqHeight > 0) {
            int width = options.outWidth;
            int height = options.outHeight;
            if (height > reqHeight || width > reqWidth) {
                final int halfWidth = width / 2;
                final int halfHeight = height / 2;
                while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }
        }
        return inSampleSize;
    }

}
