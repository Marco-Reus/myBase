package de.bvb.mydemo.util;

import android.content.Context;
import android.widget.Toast;
import de.bvb.mydemo.common.Constant;

public class ToastUtil {

	private static boolean isDebug = Constant.isDebug;

	public static void show(Context context, String text) {
		if (isDebug) {
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}
	}
	
//	public static void showLong(Context context, String text) {
//		if (isDebug) {
//			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
//		}
//	}

}
