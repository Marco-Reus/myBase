package de.bvb.mydemo.util;

import android.util.Log;
import de.bvb.mydemo.common.Constant;

/** 日志类,点击可以定位代码行 */
public class LogUtil {
	private static boolean isDebug = Constant.isDebug;

	private final static String APP_TAG = "myApp";

	/**
	 * 获取相关数据:类名,方法名,行号等.用来定位行<br>
	 * at cn.utils.MainActivity.onCreate(MainActivity.java:17) 就是用來定位行的代碼<br>
	 * 
	 * @return [ Thread:main, at
	 *         cn.utils.MainActivity.onCreate(MainActivity.java:17)]
	 */
	private static String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts != null) {
			for (StackTraceElement st : sts) {
				if (st.isNativeMethod()) {
					continue;
				}
				if (st.getClassName().equals(Thread.class.getName())) {
					continue;
				}
				if (st.getClassName().equals(LogUtil.class.getName())) {
					continue;
				}
				return "[ Thread:" + Thread.currentThread().getName() + ", at " + st.getClassName() + "." + st.getMethodName() + "(" + st.getFileName() + ":"
				        + st.getLineNumber() + ")" + " ]";
			}
		}
		return null;
	}

	//
	// public static void e(String msg) {
	// if (isDebug) {
	// Log.e(APP_TAG, getMsgFormat(msg));
	// }
	// }
	//
	// public static void e(String tag, String msg) {
	// if (isDebug) {
	// Log.e(tag, getMsgFormat(msg));
	// }
	// }
	//
	public static void w(String msg) {
		if (isDebug) {
			Log.w(APP_TAG, getMsgFormat(msg));
		}
	}

	// public static void w(String tag, String msg) {
	// if (isDebug) {
	// Log.w(tag, getMsgFormat(msg));
	// }
	// }

	/** 输出格式定义 */
	private static String getMsgFormat(String msg) {
		return msg + " ;" + getFunctionName();
	}

}