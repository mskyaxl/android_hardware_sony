package egg.lab.extra

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import egg.lab.extra.utils.ChargingCtrl



private const val TAG : String = "EggExtra";

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private var mChargingCtrl : ChargingCtrl? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            this.mChargingCtrl = ChargingCtrl()
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            // Check state from Settings
            findPreference<SwitchPreference>("switchCharger")!!.isChecked =
                Settings.System.getInt(context?.contentResolver, "egg_extra_charging_disable", 0) == 1

            // Set state into Settings
            findPreference<SwitchPreference>("switchCharger")!!.setOnPreferenceChangeListener { _, state ->
                if (state as Boolean) {
                    mChargingCtrl?.disableCharging(context)
                    Settings.System.putInt(context?.contentResolver, "egg_extra_charging_disable", 1)
                } else {
                    mChargingCtrl?.enableCharging(context)
                    Settings.System.putInt(context?.contentResolver, "egg_extra_charging_disable", 0)
                }
                true
            }

            // About
            findPreference<Preference>("about")!!.setOnPreferenceClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                builder.setTitle("EGG EXTRA")
                builder.setMessage(R.string.about_dialog)
                builder.setCancelable(false)
                builder.setPositiveButton("OKAY") { dialog, _ -> dialog.dismiss() }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                true
            }
        }


    }
}
