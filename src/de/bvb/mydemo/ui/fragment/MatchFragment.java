package de.bvb.mydemo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import de.bvb.mydemo.R;
import de.bvb.mydemo.adapter.ImageLoaderAdapter;
import de.bvb.mydemo.common.Constant.Images;
import de.bvb.mydemo.ui.BaseFragment;
import de.bvb.mydemo.util.lrcCache.ImageLoader;

public class MatchFragment extends BaseFragment  {
    private int imageViewWidth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_match, null);

        int screenWidth = getScreenMetrics(getActivity()).widthPixels;
        int space = (int) dp2px(getActivity(), 20f);
        imageViewWidth = (screenWidth - space) / 3;

        GridView gv = (GridView) view.findViewById(R.id.gv);
        ImageLoader imageLoader = ImageLoader.build(getActivity());
        ImageLoaderAdapter adapter = new ImageLoaderAdapter(getActivity(), gv, imageLoader, Images.imageUrls, imageViewWidth);
        gv.setAdapter(adapter);
        return view;
    }

    public DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
