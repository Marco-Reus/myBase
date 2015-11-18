package de.bvb.mydemo;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import de.bvb.mydemo.common.DeviceInfo;
import de.bvb.mydemo.util.LanguageUtil;

public class MyApplication extends Application {

	private static MyApplication instance;

	public static MyApplication getInstance() {
		if (instance == null) {
			instance = new MyApplication();
		}
		return instance;
	}

	public static Context getContext() {
		return getInstance();
	}

	/** Activity管理列表 */
	private List<Activity> activityList = new LinkedList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		// 根据系统语言设置应用语言
		LanguageUtil.resetAppLanguage(this);

		// 设备信息初始化
		initDeciceInfo();

	}

	public void initDeciceInfo() {
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		// 初始化屏幕宽高
		DeviceInfo.density = dm.density;
		DeviceInfo.width = dm.widthPixels;
		DeviceInfo.height = dm.heightPixels;
	}

	/** 增加activity到链表中 */
	public void addActivity(Activity activity) {
		if (!activityList.contains(activity)) {
			activityList.add(activity);
		}
	}

	/** 从acitivity链表中移除某个activity */
	public void removeActivity(Activity activity) {
		if (!activityList.contains(activity)) {
			activityList.remove(activity);
		}
	}

	/** 退出app */
	public void exitApp() {
		exitAllActivity();
		System.exit(0);
	}

	/** 重启app */
	public void restart() {
		Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		exitApp();
	}

	/** 退出所有activity */
	public void exitAllActivity() {
		for (Activity activity : activityList) {
			if (activity != null) {
				activity.finish();
			}
		}
	}
}
