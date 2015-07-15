package de.bvb.mybistudy.ui.widget;

import android.content.Context;
import android.preference.Preference;
import de.bvb.mybistudy.R;

public class SettingsPreference extends Preference {

	public SettingsPreference(Context context) {
	    super(context);
	    setLayoutResource(R.layout.preferences_settings);
    }

}
