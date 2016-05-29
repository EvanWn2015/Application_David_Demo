package idv.david.preferencefragmentex;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class InfoFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{
    private SharedPreferences sharedPref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.personal_info);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference etPref = (EditTextPreference) pref;
            etPref.setSummary(sharedPref.getString("prefName", getString(R.string.pref_name_summary)));
        }
    }


}
