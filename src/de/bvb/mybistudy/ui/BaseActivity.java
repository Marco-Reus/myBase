package de.bvb.mybistudy.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.bvb.mybistudy.MyApplication;
import de.bvb.mybistudy.R;
import de.bvb.mybistudy.common.Constant;
import de.bvb.mybistudy.util.LanguageUtil;

public class BaseActivity extends Activity {
	public Activity context;

	/** 提示框管理列表 */
	private List<Dialog> list = new ArrayList<Dialog>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		/** 设置主题 */
		setAppTheme();
		/** 设置语言 */
		LanguageUtil.resetAppLanguage(context);
		/** 添加当前activity至activity管理列表 */
		MyApplication.getInstance().addActivity(context);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.getInstance().removeActivity(context);
	}

	/** 加载中、登陆，只需要文字的提示对话框 ,设置为0使用默认图片动画 */
	public void showProgressDialog(String content) {
		showProgressDialog(content, 0);
	}

	/** 加载中、登陆，只需要文字的提示对话框 ,设置为0使用默认图片动画 */
	public void showProgressDialog(String content, int resId) {
		Dialog dialog = new Dialog(context);
		// 使用自定义布局的对话框
		RelativeLayout rl_dialog = (RelativeLayout) View.inflate(context, R.layout.view_dialog, null);
		// 允许点击后终止对话框和后续代码的执行
		dialog.setCanceledOnTouchOutside(true);
		// 去掉对话框的标题
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		TextView tv = (TextView) rl_dialog.findViewById(R.id.tv_dialog);
		ImageView iv = (ImageView) rl_dialog.findViewById(R.id.iv_dialog);
		// 设置文字信息
		if (TextUtils.isEmpty(content)) {
			tv.setVisibility(View.GONE);
		} else {
			tv.setText(content + Constant.CommonChar.ellipsis);
		}
		// 设置图片信息
		if (resId == 0) {
			iv.setBackgroundResource(R.drawable.dialog_loadding);
			iv.startAnimation(AnimationUtils.loadAnimation(context, R.anim.loading));
		} else {
			iv.setBackgroundResource(resId);
		}
		// 将view添加进对话框
		dialog.setContentView(rl_dialog);
		// 显示对话框
		dialog.show();
		list.add(dialog);
	}

	/** 取消进度提示框 */
	public void dismissProgressDialog() {
		if (list.size() > 0)
			for (int i = 0; i < list.size(); i++)
				if (list.get(i).isShowing())
					list.get(i).dismiss();
	}

	/** 设置主题 */
	private void setAppTheme() {
		// TODO ..
	}

}
