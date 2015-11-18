package de.bvb.mydemo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import de.bvb.mydemo.R;
import de.bvb.mydemo.util.lrcCache.ImageLoader;

public class ImageLoaderAdapter extends BaseAdapter {
    private Context context;
    private GridView gv;
    private ImageLoader imageLoader;
    private String[] urls;
    private int imageViewWidth;

    private boolean isGridViewIdle;

    public ImageLoaderAdapter(Context context, GridView gv, ImageLoader imageLoader, String[] urls, int imageViewWidth) {
        super();
        this.context = context;
        this.gv = gv;
        this.imageLoader = imageLoader;
        this.urls = urls;
        this.imageViewWidth = imageViewWidth;
        this.gv.setOnScrollListener(onScrollListener);
        this.gv.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.adapter_fragment_match_image_loader, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageView iv = holder.iv;
        String tag = (String) iv.getTag();
        String url = urls[position];
        if (!url.equals(tag)) {
            iv.setImageResource(R.drawable.ic_launcher);
        }
        // 首次显示页面的时候加载数据
        if (isFristEnter) {
            iv.setTag(url);
            imageLoader.bindBitmap(url, iv, imageViewWidth, imageViewWidth);
            if (gv.getLastVisiblePosition() == position) {
                isFristEnter = false;
            }
        }
        // 滑动的时候加载数据
        if (isGridViewIdle) {
            iv.setTag(url);
            imageLoader.bindBitmap(url, iv, imageViewWidth, imageViewWidth);
        }

        return convertView;
    }

    boolean isFristEnter = true;
    OnScrollListener onScrollListener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            isGridViewIdle = scrollState == OnScrollListener.SCROLL_STATE_IDLE;
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                notifyDataSetChanged();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

    };
    OnItemClickListener onItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            ImageView iv = new ImageView(context);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(layoutParams);
            builder.setView(iv);
            imageLoader.bindBitmap(urls[position], iv);
            builder.show();

        }
    };

    @Override
    public int getCount() {
        return urls == null ? 0 : urls.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {
        ImageView iv;
    }

}
