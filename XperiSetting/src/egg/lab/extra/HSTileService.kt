package egg.lab.extra

import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import egg.lab.extra.utils.ChargingCtrl

class HSTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()

        // Check Settings when listening
        val state = Settings.System.getInt(contentResolver, "egg_extra_charging_disable")
        if (state == 1) {
            qsTile.state = Tile.STATE_ACTIVE
        } else {
            qsTile.state = Tile.STATE_INACTIVE
        }
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()

        if (qsTile.state == Tile.STATE_ACTIVE) {
            qsTile.state = Tile.STATE_INACTIVE
        } else {
            qsTile.state = Tile.STATE_ACTIVE
        }
        qsTile.updateTile()
    }
}
