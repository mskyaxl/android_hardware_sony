package egg.lab.extra.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import egg.lab.extra.R
import vendor.semc.hardware.charger.V1_0.ICharger;
import android.util.Log
import android.os.RemoteException

private const val TAG : String = "ChargingCtrl";

class ChargingCtrl {

    private var mICharger : ICharger? = null;

    constructor() {
        Log.e(TAG, "ChargingCtrl start()");
        connectHidlService();
    }

    private fun connectHidlService() {
        if (this.mICharger != null) {
            return;
        }
        this.mICharger = ICharger.getService();
        if (this.mICharger == null) {
            Log.e(TAG, "IChargerWrapper: Can not get ICharger service.\n");
        }
    }

    // Disable charging
    fun disableCharging(context: Context?): Unit {
        // SmartChargeActivate
        if (this.mICharger != null) {
            try {
                this.mICharger?.setSmartChargeAction(2);
            } catch (unused : RemoteException) {
                Log.e(TAG, "IChargerWrapper: Occurred RemoteException.\n");
            }
        }
    }

    // Enable charging
    fun enableCharging(context: Context?): Unit {
        // SmartChargeSuspend
        if (this.mICharger != null) {
            try {
                this.mICharger?.setSmartChargeAction(3);
            } catch (unused : RemoteException) {
                Log.e(TAG, "IChargerWrapper: Occurred RemoteException.\n");
            }
        }
    }
}
