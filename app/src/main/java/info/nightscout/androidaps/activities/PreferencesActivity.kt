package info.nightscout.androidaps.activities

import android.content.Context
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import info.nightscout.androidaps.MainApp
import info.nightscout.androidaps.R
import info.nightscout.androidaps.utils.LocaleHelper

class PreferencesActivity : NoSplashAppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartScreenCallback {
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) { //AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)
        title = MainApp.gs(R.string.nav_preferences)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val myPreferenceFragment = MyPreferenceFragment()
        id = intent.getIntExtra("id", -1)
        val args = Bundle()
        args.putInt("id", id)
        myPreferenceFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, myPreferenceFragment).commit()
    }

    override fun onPreferenceStartScreen(caller: PreferenceFragmentCompat, pref: PreferenceScreen): Boolean {
        val fragment = MyPreferenceFragment()
        val args = Bundle()
        args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, pref.key)
        args.putInt("id", id)
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment, pref.key)
            .addToBackStack(pref.key)
            .commit()
        return true
    }

    public override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.wrap(newBase))
    }
}