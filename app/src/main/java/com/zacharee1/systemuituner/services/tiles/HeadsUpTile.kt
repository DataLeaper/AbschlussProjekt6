
package com.zacharee1.systemuituner.services.tiles

import android.annotation.TargetApi
import android.database.ContentObserver
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.service.quicksettings.Tile
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.*
import kotlinx.coroutines.launch

@TargetApi(Build.VERSION_CODES.N)
class HeadsUpTile : CoroutineTileService() {
    private val observer = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean, uri: Uri) {
            if (uri == Settings.Global.getUriFor(Settings.Global.HEADS_UP_NOTIFICATIONS_ENABLED)) {
                updateState()
            }
        }
    }

    private val isEnabled: Boolean
        get() = getSetting(
            SettingsType.GLOBAL,
            Settings.Global.HEADS_UP_NOTIFICATIONS_ENABLED,
            0
        ) == "1"

    override fun onStartListening() {
        contentResolver.registerContentObserver(Settings.Global.CONTENT_URI, true, observer)

        updateState()
    }

    override fun onStopListening() {
        try {
            contentResolver.unregisterContentObserver(observer)
        } catch (_: Exception) {}
    }

    private fun updateState() {
        qsTile?.state = if (isEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        setIcon()
        qsTile?.safeUpdateTile()
    }

    private fun setIcon() {
        qsTile?.icon = Icon.createWithResource(
            this,
            if (isEnabled) R.drawable.ic_baseline_notifications_active_24 else R.drawable.ic_baseline_notifications_off_24
        )
    }

    override fun onClick() {
        launch {
            writeSetting(SettingsType.GLOBAL, Settings.Global.HEADS_UP_NOTIFICATIONS_ENABLED, if (isEnabled) 0 else 1, saveOption = true)
            updateState()
        }

        super.onClick()
    }
}