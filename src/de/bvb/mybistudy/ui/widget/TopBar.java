package de.bvb.mybistudy.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.bvb.mybistudy.R;

public class TopBar extends RelativeLayout implements View.OnClickListener {

	private Context context;
	private RelativeLayout rl_bop_bar;
	private ImageView iv_top_bar_left;
	private TextView tv_top_title;
	private RelativeLayout rl_top_bar_right;
	private TextView tv_top_bar_right;
	private ImageView iv_top_bar_right;

	public TopBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TopBar(Context context) {
		this(context, null);
	}

	public TopBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
		initEvent();
	}

	private void initView() {
		View.inflate(context, R.layout.view_top_bar, this);
		rl_bop_bar = (RelativeLayout) findViewById(R.id.rl_bop_bar);
		rl_top_bar_right = (RelativeLayout) findViewById(R.id.rl_top_bar_right);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_bar_right = (TextView) findViewById(R.id.tv_top_bar_right);
		iv_top_bar_left = (ImageView) findViewById(R.id.iv_top_bar_left);
		iv_top_bar_right = (ImageView) findViewById(R.id.iv_top_bar_right);
	}

	private void initEvent() {
		iv_top_bar_left.setOnClickListener(this);
		rl_top_bar_right.setOnClickListener(this);
	}

	/** 设置顶部栏整个背景的颜色 */
	public void setTopBarBackgroundColor(int color) {
		rl_bop_bar.setBackgroundColor(color);
	}

	/** 设置左边图标 */
	public void setTopBarLeftIcon(int resId) {
		iv_top_bar_left.setImageResource(resId);
	}

	/** 隐藏左边图标 */
	public void setTopBarLeftGone() {
		iv_top_bar_left.setVisibility(View.GONE);
	}

	/** 设置中间标题 */
	public void setTopBarTitle(String tilte) {
		tv_top_title.setText(tilte);
	}

	/** 设置顶部栏右边显示 */
	public void setTopBarRightVisible() {
		rl_top_bar_right.setVisibility(View.VISIBLE);
	}

	/** 设置顶部栏右边隐藏 */
	public void setTopBarRightGone() {
		rl_top_bar_right.setVisibility(View.GONE);
	}

	/** 设置右边文字 */
	public void setTopBarRightText(String rightText) {
		iv_top_bar_right.setVisibility(View.GONE);
		tv_top_bar_right.setText(rightText);
	}

	/** 设置右边图标 */
	public void setTopBarRightIcon(int resId) {
		tv_top_bar_right.setVisibility(View.GONE);
		iv_top_bar_right.setImageResource(resId);
	}

	/** 顶部栏监听接口 */
	public interface TopBarClickListener {
		/** 顶部栏右边被点击 */
		void onClickRight();
	}

	TopBarClickListener listener;

	/** 设置顶部栏监听事件 */
	public void setTopBarClickListener(TopBarClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_top_bar_left:
			((Activity) context).finish();
			break;
		case R.id.rl_top_bar_right:
			if (listener != null) {
				listener.onClickRight();
			}
			break;
		}
	}

}
