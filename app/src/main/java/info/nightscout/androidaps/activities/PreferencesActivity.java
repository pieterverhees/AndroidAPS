package info.nightscout.androidaps.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import info.nightscout.androidaps.R;

public class PreferencesActivity extends NoSplashAppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //AndroidInjection.inject(this);
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        MyPreferenceFragment myPreferenceFragment = new MyPreferenceFragment();
        id = getIntent().getIntExtra("id", -1);
        Bundle args = new Bundle();
        args.putInt("id", id);
        myPreferenceFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, myPreferenceFragment).commit();
    }


    @Override public boolean onPreferenceStartScreen(PreferenceFragmentCompat caller, PreferenceScreen pref) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MyPreferenceFragment fragment = new MyPreferenceFragment();
        Bundle args = new Bundle();
        args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, pref.getKey());
        args.putInt("id", id);
        fragment.setArguments(args);
        ft.replace(android.R.id.content, fragment, pref.getKey());
        ft.addToBackStack(pref.getKey());
        ft.commit();
        return true;
    }
}
