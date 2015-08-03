package de.bvb.mybistudy.ui.fragment;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import de.bvb.mybistudy.R;
import de.bvb.mybistudy.ui.widget.SeekBarDialogPreference;
import de.bvb.mybistudy.ui.widget.SeekBarPreference;
import de.bvb.mybistudy.util.LanguageUtil;
import de.bvb.mybistudy.util.SPUtil;

public class SettingsFragment extends PreferenceFragment implements OnPreferenceChangeListener, OnPreferenceClickListener {
	// 参考 http://blog.csdn.net/flowingflying/article/details/16946467

	/** 保存的首选项的名字 */
	private final String spFileName = "settings";
	private Context context;

	private CheckBoxPreference checkBoxPreference_developer_mode;
	private ListPreference listPreference_current_app_language;
	private MultiSelectListPreference multiSelectListPreference_msg_notify;
	private EditTextPreference editTextPreference_server_url;
	private SeekBarDialogPreference seekBarDialogPreference_seekbar_dialog;
	private SeekBarPreference seekBarPreference_seekbar;

	/** 既作为控件的key(相当于普通控件的id,只不过没有存放在R文件中,需要自己写一个对象),也作为存取的首选项的key */
	private String key_current_app_language = "current_app_language";
	private String key_msg_notify = "msg_notify";
	private String key_developer_mode = "developer_mode";
	private String key_server_url = "server_url";
	private String key_seekbar_dialog = "seekbar_dialog";
	private String key_seekbar = "seekbar";

	private String text_server_url;
	private int value_seekbar_dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();

		/** 设置保存的首选项的名字 */
		getPreferenceManager().setSharedPreferencesName(spFileName);
		addPreferencesFromResource(R.xml.prefs);

		initView();
	}

	@SuppressWarnings("unchecked")
	private void initView() {
		/** 设置语言 */
		// 获取控件,对应activity中的findViewById
		listPreference_current_app_language = (ListPreference) findPreference(key_current_app_language);
		// 从首选项中获取初始化时的值
		listPreference_current_app_language.setValue((String) SPUtil.get(context, spFileName, key_current_app_language, "Chinese"));
		// 设置点击事件:值变化后的处理
		listPreference_current_app_language.setOnPreferenceChangeListener(this);
		// TODO 如何自定义弹出来的对话框的样式

		/** 设置开发者模式 */
		checkBoxPreference_developer_mode = (CheckBoxPreference) findPreference(key_developer_mode);
		checkBoxPreference_developer_mode.setChecked((boolean) SPUtil.get(context, spFileName, key_developer_mode, false));

		/** 设置消息通知列表 */
		multiSelectListPreference_msg_notify = (MultiSelectListPreference) findPreference(key_msg_notify);
		multiSelectListPreference_msg_notify.setOnPreferenceChangeListener(this);
		multiSelectListPreference_msg_notify.setValues((Set<String>) SPUtil.get(context, spFileName, key_msg_notify, new HashSet<String>()));

		/** 设置服务器地址 */
		editTextPreference_server_url = (EditTextPreference) findPreference(key_server_url);
		text_server_url = (String) SPUtil.get(context, spFileName, key_server_url, "");
		editTextPreference_server_url.setSummary(text_server_url);
		editTextPreference_server_url.setOnPreferenceChangeListener(this);
		editTextPreference_server_url.setOnPreferenceClickListener(this); // 设置监听事件:使光标移动到末尾
		// TODO title和summay 的显示格式有问题

		/** 进度条对话框 */
		seekBarDialogPreference_seekbar_dialog = (SeekBarDialogPreference) findPreference(key_seekbar_dialog);
		value_seekbar_dialog = (int) SPUtil.get(context, spFileName, key_seekbar_dialog, -1);
		seekBarDialogPreference_seekbar_dialog.setSummary(value_seekbar_dialog != -1 ? (value_seekbar_dialog + "") : "点击选择大小");

		/** 进度条 */
		seekBarPreference_seekbar = (SeekBarPreference) findPreference(key_seekbar);
		seekBarPreference_seekbar.setProgress((int) SPUtil.get(context, spFileName, key_seekbar, -1));
	} 

	@SuppressWarnings("unchecked")
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (key_current_app_language.equalsIgnoreCase(preference.getKey())) {
			listPreference_current_app_language.setValue((String) newValue);
			// 如果值发生变化,就修改当前app的语言.
			LanguageUtil.changeAppLanguage(context, "English".equals(newValue.toString()) ? 2 : 1);
		} else if (key_msg_notify.equalsIgnoreCase(preference.getKey())) {
			multiSelectListPreference_msg_notify.setValues((Set<String>) newValue);
		} else if (key_server_url.equalsIgnoreCase(preference.getKey())) {
			text_server_url = (String) newValue;
			editTextPreference_server_url.setSummary(text_server_url);
		}
		return true;
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (key_server_url.equalsIgnoreCase(preference.getKey())) {
			editTextPreference_server_url.getEditText().setSelection(text_server_url.length());
			return true;
		}
		return false;
	}

}
