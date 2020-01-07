package info.nightscout.androidaps.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;

import java.util.Arrays;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.MainApp;
import info.nightscout.androidaps.R;
import info.nightscout.androidaps.data.Profile;
import info.nightscout.androidaps.events.EventPreferenceChange;
import info.nightscout.androidaps.events.EventRebuildTabs;
import info.nightscout.androidaps.interfaces.PluginBase;
import info.nightscout.androidaps.interfaces.PluginType;
import info.nightscout.androidaps.plugins.aps.loop.LoopPlugin;
import info.nightscout.androidaps.plugins.aps.openAPSAMA.OpenAPSAMAPlugin;
import info.nightscout.androidaps.plugins.aps.openAPSMA.OpenAPSMAPlugin;
import info.nightscout.androidaps.plugins.aps.openAPSSMB.OpenAPSSMBPlugin;
import info.nightscout.androidaps.plugins.bus.RxBus;
import info.nightscout.androidaps.plugins.constraints.safety.SafetyPlugin;
import info.nightscout.androidaps.plugins.general.automation.AutomationPlugin;
import info.nightscout.androidaps.plugins.general.careportal.CareportalPlugin;
import info.nightscout.androidaps.plugins.general.nsclient.NSClientPlugin;
import info.nightscout.androidaps.plugins.general.smsCommunicator.SmsCommunicatorPlugin;
import info.nightscout.androidaps.plugins.general.tidepool.TidepoolPlugin;
import info.nightscout.androidaps.plugins.general.wear.WearPlugin;
import info.nightscout.androidaps.plugins.general.xdripStatusline.StatusLinePlugin;
import info.nightscout.androidaps.plugins.insulin.InsulinOrefFreePeakPlugin;
import info.nightscout.androidaps.plugins.pump.combo.ComboPlugin;
import info.nightscout.androidaps.plugins.pump.danaR.DanaRPlugin;
import info.nightscout.androidaps.plugins.pump.danaRKorean.DanaRKoreanPlugin;
import info.nightscout.androidaps.plugins.pump.danaRS.DanaRSPlugin;
import info.nightscout.androidaps.plugins.pump.danaRv2.DanaRv2Plugin;
import info.nightscout.androidaps.plugins.pump.insight.LocalInsightPlugin;
import info.nightscout.androidaps.plugins.pump.medtronic.MedtronicPumpPlugin;
import info.nightscout.androidaps.plugins.pump.virtual.VirtualPumpPlugin;
import info.nightscout.androidaps.plugins.sensitivity.SensitivityAAPSPlugin;
import info.nightscout.androidaps.plugins.sensitivity.SensitivityOref0Plugin;
import info.nightscout.androidaps.plugins.sensitivity.SensitivityOref1Plugin;
import info.nightscout.androidaps.plugins.sensitivity.SensitivityWeightedAveragePlugin;
import info.nightscout.androidaps.plugins.source.DexcomPlugin;
import info.nightscout.androidaps.plugins.source.EversensePlugin;
import info.nightscout.androidaps.plugins.source.GlimpPlugin;
import info.nightscout.androidaps.plugins.source.PoctechPlugin;
import info.nightscout.androidaps.plugins.source.TomatoPlugin;
import info.nightscout.androidaps.utils.OKDialog;
import info.nightscout.androidaps.utils.SP;
import info.nightscout.androidaps.utils.SafeParse;

/**
 * Created by adrian on 2019-12-23.
 */
public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, HasAndroidInjector {
    private Integer id;

    @Inject AutomationPlugin automationPlugin;
    @Inject DanaRPlugin danaRPlugin;
    @Inject DanaRKoreanPlugin danaRKoreanPlugin;
    @Inject DanaRv2Plugin danaRv2Plugin;
    @Inject DanaRSPlugin danaRSPlugin;
    @Inject CareportalPlugin careportalPlugin;
    @Inject InsulinOrefFreePeakPlugin insulinOrefFreePeakPlugin;
    @Inject LoopPlugin loopPlugin;
    @Inject OpenAPSAMAPlugin openAPSAMAPlugin;
    @Inject OpenAPSMAPlugin openAPSMAPlugin;
    @Inject OpenAPSSMBPlugin openAPSSMBPlugin;
    @Inject SafetyPlugin safetyPlugin;
    @Inject DexcomPlugin dexcomPlugin;
    @Inject EversensePlugin eversensePlugin;
    @Inject GlimpPlugin glimpPlugin;
    @Inject PoctechPlugin poctechPlugin;
    @Inject TomatoPlugin tomatoPlugin;
    @Inject SmsCommunicatorPlugin smsCommunicatorPlugin;
    @Inject StatusLinePlugin statusLinePlugin;
    @Inject TidepoolPlugin tidepoolPlugin;
    @Inject VirtualPumpPlugin virtualPumpPlugin;
    @Inject WearPlugin wearPlugin;

    @Inject DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        id = args.getInt("id");
    }

    private void addPreferencesFromResourceIfEnabled(PluginBase p, String rootKey) {
        if (p.isEnabled() && p.getPreferencesId() != -1)
            setPreferencesFromResource(p.getPreferencesId(), rootKey);
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        if (savedInstanceState != null && savedInstanceState.containsKey("id")) {
            id = savedInstanceState.getInt("id");
        }

        if (id != -1) {
            addPreferencesFromResource(id);
        } else {

            if (!Config.NSCLIENT) {
                addPreferencesFromResource(R.xml.pref_password);
            }
            addPreferencesFromResource(R.xml.pref_general);
            addPreferencesFromResource(R.xml.pref_age);

            addPreferencesFromResource(R.xml.pref_overview);

            addPreferencesFromResourceIfEnabled(eversensePlugin, rootKey);
            addPreferencesFromResourceIfEnabled(dexcomPlugin, rootKey);
            addPreferencesFromResourceIfEnabled(tomatoPlugin, rootKey);
            addPreferencesFromResourceIfEnabled(poctechPlugin, rootKey);
            addPreferencesFromResourceIfEnabled(glimpPlugin, rootKey);
            addPreferencesFromResourceIfEnabled(careportalPlugin, rootKey);
            addPreferencesFromResourceIfEnabled(safetyPlugin, rootKey);
            if (Config.APS) {
                addPreferencesFromResourceIfEnabled(loopPlugin, rootKey);
                addPreferencesFromResourceIfEnabled(openAPSMAPlugin, rootKey);
                addPreferencesFromResourceIfEnabled(openAPSAMAPlugin, rootKey);
                addPreferencesFromResourceIfEnabled(openAPSSMBPlugin, rootKey);
            }

            addPreferencesFromResourceIfEnabled(SensitivityAAPSPlugin.getPlugin(), rootKey);
            addPreferencesFromResourceIfEnabled(SensitivityWeightedAveragePlugin.getPlugin(), rootKey);
            addPreferencesFromResourceIfEnabled(SensitivityOref0Plugin.getPlugin(), rootKey);
            addPreferencesFromResourceIfEnabled(SensitivityOref1Plugin.getPlugin(), rootKey);

            if (Config.PUMPDRIVERS) {
                addPreferencesFromResourceIfEnabled(danaRPlugin, rootKey);
                addPreferencesFromResourceIfEnabled(danaRKoreanPlugin, rootKey);
                addPreferencesFromResourceIfEnabled(danaRv2Plugin, rootKey);
                addPreferencesFromResourceIfEnabled(danaRSPlugin, rootKey);
                addPreferencesFromResourceIfEnabled(LocalInsightPlugin.getPlugin(), rootKey);
                addPreferencesFromResourceIfEnabled(ComboPlugin.getPlugin(), rootKey);
                addPreferencesFromResourceIfEnabled(MedtronicPumpPlugin.getPlugin(), rootKey);
            }

            if (!Config.NSCLIENT) {
                addPreferencesFromResourceIfEnabled(virtualPumpPlugin, rootKey);
            }

            addPreferencesFromResourceIfEnabled(insulinOrefFreePeakPlugin, rootKey);

            addPreferencesFromResourceIfEnabled(NSClientPlugin.getPlugin(), rootKey);
            addPreferencesFromResourceIfEnabled(tidepoolPlugin, rootKey);
            addPreferencesFromResourceIfEnabled(smsCommunicatorPlugin, rootKey);
            addPreferencesFromResourceIfEnabled(automationPlugin, rootKey);

            addPreferencesFromResource(R.xml.pref_others);
            addPreferencesFromResource(R.xml.pref_datachoices);

            addPreferencesFromResourceIfEnabled(wearPlugin, rootKey);
            addPreferencesFromResourceIfEnabled(statusLinePlugin, rootKey);
        }

        initSummary(getPreferenceScreen());

        for (PluginBase plugin : MainApp.getPluginsList()) {
            plugin.preprocessPreferences(this);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        RxBus.Companion.getINSTANCE().send(new EventPreferenceChange(key));
        if (key.equals(MainApp.gs(R.string.key_language))) {
            RxBus.Companion.getINSTANCE().send(new EventRebuildTabs(true));
            //recreate() does not update language so better close settings
            getActivity().finish();
        }
        if (key.equals(MainApp.gs(R.string.key_short_tabtitles))) {
            RxBus.Companion.getINSTANCE().send(new EventRebuildTabs());
        }
        if (key.equals(MainApp.gs(R.string.key_units))) {
            getActivity().recreate();
            return;
        }
        if (key.equals(MainApp.gs(R.string.key_openapsama_useautosens)) && SP.getBoolean(R.string.key_openapsama_useautosens, false)) {
            OKDialog.show(getActivity(), MainApp.gs(R.string.configbuilder_sensitivity), MainApp.gs(R.string.sensitivity_warning));
        }
        updatePrefSummary(findPreference(key));
    }

    private static void adjustUnitDependentPrefs(Preference pref) {
        // convert preferences values to current units
        String[] unitDependent = new String[]{
                MainApp.gs(R.string.key_hypo_target),
                MainApp.gs(R.string.key_activity_target),
                MainApp.gs(R.string.key_eatingsoon_target),
                MainApp.gs(R.string.key_high_mark),
                MainApp.gs(R.string.key_low_mark)
        };
        if (Arrays.asList(unitDependent).contains(pref.getKey())) {
            EditTextPreference editTextPref = (EditTextPreference) pref;
            String converted = Profile.toCurrentUnitsString(SafeParse.stringToDouble(editTextPref.getText()));
            editTextPref.setSummary(converted);
            editTextPref.setText(converted);
        }
    }

    private static void updatePrefSummary(Preference pref) {
        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
        }
        if (pref instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference) pref;
            if (pref.getKey().contains("password") || pref.getKey().contains("secret")) {
                pref.setSummary("******");
            } else if (editTextPref.getText() != null) {
                ((EditTextPreference) pref).setDialogMessage(editTextPref.getDialogMessage());
                pref.setSummary(editTextPref.getText());
            } else {
                for (PluginBase plugin : MainApp.getPluginsList()) {
                    plugin.updatePreferenceSummary(pref);
                }
            }
        }
        if (pref != null)
            adjustUnitDependentPrefs(pref);
    }

    public static void initSummary(Preference p) {
        if (p instanceof PreferenceGroup) {
            PreferenceGroup pGrp = (PreferenceGroup) p;
            for (int i = 0; i < pGrp.getPreferenceCount(); i++) {
                initSummary(pGrp.getPreference(i));
            }
        } else {
            updatePrefSummary(p);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id", id);
    }
}
