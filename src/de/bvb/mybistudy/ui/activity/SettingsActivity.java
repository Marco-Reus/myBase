package de.bvb.mybistudy.ui.activity;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import de.bvb.mybistudy.R;
import de.bvb.mybistudy.common.Constant;
import de.bvb.mybistudy.util.LanguageUtil;
import de.bvb.mybistudy.util.SPUtil;
import de.bvb.mybistudy.util.ToastUtil;

public class SettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener {

	private final String sp_name = Constant.SP_FILE_NAME;
	private ListPreference lp_language;
	/** 既作为控件的key(相当于普通控件的id,只不过没有存放在R文件中,需要自己写一个对象),也作为存取的首选项的key */
	private String key_lp_language = "current_app_language";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		/** 设置保存的首选项的名字 */
		getPreferenceManager().setSharedPreferencesName(sp_name);
		addPreferencesFromResource(R.layout.activity_settings);

		lp_language = (ListPreference) findPreference(key_lp_language);
		// 设置默认值
		lp_language.setValue((String) SPUtil.get(this, key_lp_language, "Chinese"));

		lp_language.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		ToastUtil.showShort(this, newValue.toString());
		if ("Chinese".equals(newValue.toString())) {
			lp_language.setValue((String) newValue);
			LanguageUtil.changeAppLanguage(this, 1);
		} else if ("English".equals(newValue.toString())) {
			lp_language.setValue((String) newValue);
			LanguageUtil.changeAppLanguage(this, 2);
		}
		return true;
	}

}
