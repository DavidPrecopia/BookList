package com.example.android.precopia.booklisttest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
	}
	
	public static class SearchPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
		@Override
		public void onCreate(@Nullable Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.settings_main);
			
			Preference maxResultsPreference = findPreference(getString(R.string.settings_max_results_key));
			bindPreferenceSummaryToValue(maxResultsPreference);
		}
		
		/**
		 *  Updates the summary so that it displays the current value stored in SharedPreferences
		 * @param maxResultsPreference
		 */
		private void bindPreferenceSummaryToValue(Preference maxResultsPreference) {
			maxResultsPreference.setOnPreferenceChangeListener(this);
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(maxResultsPreference.getContext());
			String preferenceString = sharedPreferences.getString(maxResultsPreference.getKey(), "");
			onPreferenceChange(maxResultsPreference, preferenceString);
		}
		
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			preference.setSummary(newValue.toString());
			return true;
		}
	}
}