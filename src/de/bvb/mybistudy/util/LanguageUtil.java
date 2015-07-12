package de.bvb.mybistudy.util;

import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import de.bvb.mybistudy.MyApplication;
import de.bvb.mybistudy.common.Constant;

/** 中英文切换工具类 */
public class LanguageUtil {

	/** 改变app显示语言,1=中文，2=英文 */
	public static void changeAppLanguage(Context context, int mode) {
		if (!isSupportBothLanuage(context)) {
			// 如果不支持中英文双语，则退出
			return;
		}
		Configuration configuration = context.getResources().getConfiguration();
		switch (mode) {
		case 1:
		default:
			configuration.locale = Locale.CHINA;
			SPUtil.put(context, Constant.SP_KEY_LANGUAGE, false);
			break;
		case 2:
			configuration.locale = Locale.ENGLISH;
			SPUtil.put(context, Constant.SP_KEY_LANGUAGE, true);
			break;
		}
		SPUtil.put(context, Constant.SP_KEY_LANGUAGE_HAS_SET, true);
		context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
		MyApplication.getInstance().restart();
	}

	/** 重置app语言,根据系统语言来确定，非英文文的，默认用中文 */
	public static void resetAppLanguage(Context context) {

		if (!isSupportBothLanuage(context)) {
			// 如果不支持中英文双语，则退出
			return;
		}

		Configuration configuration = context.getResources().getConfiguration();

		/** 是否设置过语言 */
		boolean hasSetted = (boolean) SPUtil.get(context, Constant.SP_KEY_LANGUAGE_HAS_SET, false);
		/** 判断app当前是否是英文 */
		boolean isAppCurrentEnglish = (boolean) SPUtil.get(context, Constant.SP_KEY_LANGUAGE, false);
		/** 判断当前手机系统语言是否是英语 */
		boolean isSystemCurrentEnglish = Locale.ENGLISH.toString().equals(context.getResources().getConfiguration().locale.getLanguage());
		if (hasSetted) {
			// 如果已经设置过，用设置过的语言
			configuration.locale = isAppCurrentEnglish ? Locale.ENGLISH : Locale.CHINA;
			SPUtil.put(context, Constant.SP_KEY_LANGUAGE, isAppCurrentEnglish);
		} else {
			// 否则用系统的语言
			configuration.locale = isSystemCurrentEnglish ? Locale.ENGLISH : Locale.CHINA;
			SPUtil.put(context, Constant.SP_KEY_LANGUAGE, isSystemCurrentEnglish);
		}
		SPUtil.put(context, Constant.SP_KEY_LANGUAGE_HAS_SET, true);
		context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
	}

	/** 检查当前语言模式是否支持中英文切换,支持：返回TRUE；不支持，设置当前支持的语言，并返回false */
	@SuppressWarnings("unused")
	private static boolean isSupportBothLanuage(Context context) {
		if (Constant.LANGUAGE.SUPPORT_MODE == Constant.LANGUAGE.BOTH) {
			return true;
		} else {
			Configuration configuration = context.getResources().getConfiguration();
			configuration.locale = Constant.LANGUAGE.SUPPORT_MODE == Constant.LANGUAGE.CHINA ? Locale.CHINA : Locale.ENGLISH;
			context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
			return false;
		}
	}
}
