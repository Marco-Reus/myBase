package de.bvb.mybistudy.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.bvb.mybistudy.R;

public class SettingsFragment extends PreferenceFragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		
		addPreferencesFromResource(R.layout.activity_settings);
		
	    return super.onCreateView(inflater, container, savedInstanceState);
	}

}
