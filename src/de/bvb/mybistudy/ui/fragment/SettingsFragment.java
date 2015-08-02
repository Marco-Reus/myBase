package de.bvb.mybistudy.ui.fragment;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import de.bvb.mybistudy.R;
import de.bvb.mybistudy.util.LanguageUtil;
import de.bvb.mybistudy.util.SPUtil;

public class SettingsFragment extends PreferenceFragment implements OnPreferenceChangeListener {
	// 参考 http://blog.csdn.net/flowingflying/article/details/16946467

	/** 保存的首选项的名字 */
	private final String SP_FILE_NAME = "settings";
	private Context context;

	private CheckBoxPreference cbp_developer_mode;
	private ListPreference lp_current_app_language;
	private MultiSelectListPreference mlp_msg_nofity;

	/** 既作为控件的key(相当于普通控件的id,只不过没有存放在R文件中,需要自己写一个对象),也作为存取的首选项的key */
	private String key_current_app_language = "current_app_language";
	private String key_msg_nofity = "msg_nofity";
	private String key_developer_mode = "developer_mode";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();

		/** 设置保存的首选项的名字 */
		getPreferenceManager().setSharedPreferencesName(SP_FILE_NAME);
		addPreferencesFromResource(R.xml.prefs);

		initView();
	}

	@SuppressWarnings("unchecked")
	private void initView() {
		/** 设置语言 */
		// 获取控件,对应activity中的findViewById
		lp_current_app_language = (ListPreference) findPreference(key_current_app_language);
		// 从首选项中获取初始化时的值
		lp_current_app_language.setValue((String) SPUtil.get(context, SP_FILE_NAME, key_current_app_language, "Chinese"));
		// 设置点击事件
		lp_current_app_language.setOnPreferenceChangeListener(this);
		// TODO 如何自定义弹出来的对话框的样式

		/** 设置开发者模式 */
		cbp_developer_mode = (CheckBoxPreference) findPreference(key_developer_mode);
		cbp_developer_mode.setChecked((boolean) SPUtil.get(context, SP_FILE_NAME, key_developer_mode, false));

		/** 设置消息通知列表 */
		mlp_msg_nofity = (MultiSelectListPreference) findPreference(key_msg_nofity);
		mlp_msg_nofity.setOnPreferenceChangeListener(this);
		mlp_msg_nofity.setValues((Set<String>) SPUtil.get(context, SP_FILE_NAME, key_msg_nofity, new HashSet<String>()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (key_current_app_language.equalsIgnoreCase(preference.getKey())) {
			lp_current_app_language.setValue((String) newValue);
			// 如果值发生变化,就修改当前app的语言.
			LanguageUtil.changeAppLanguage(context, "English".equals(newValue.toString()) ? 2 : 1);
		} else if (key_msg_nofity.equalsIgnoreCase(preference.getKey())) {
			mlp_msg_nofity.setValues((Set<String>) newValue);
		}
		return true;
	}

}
