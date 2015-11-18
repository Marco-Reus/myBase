package de.bvb.mydemo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SqureImageView extends ImageView {

    public SqureImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SqureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SqureImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
