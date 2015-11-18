package de.bvb.mydemo.common;

public class Constant {

	/** Debug状态 */
	public static final boolean isDebug = true;

	/** SharePreference保存的文件名 */
	public static final String SP_FILE_NAME = "config";
	/** SharePreference保存app语言对象 */
	public static final String SP_KEY_LANGUAGE = "language_is_english";
	public static final String SP_KEY_LANGUAGE_HAS_SET = "language_has_set";

	/** 语言配置类 */
	public static final class LANGUAGE {
		/** 中文 */
		public static final int CHINA = 1;
		/** 英文 */
		public static final int ENGLISH = 2;
		/** 中英文 */
		public static final int BOTH = 3;
		/** 当前支持的语言模式 */
		public static final int SUPPORT_MODE = BOTH;
	}

	/** 字符集 */
	public static final class CommonChar {
		/** 省略号 */
		public static final String ellipsis = "\u2026";
	}

}
