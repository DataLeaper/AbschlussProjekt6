package com.zacharee1.systemuituner.services.tiles

import android.annotation.TargetApi
import android.database.ContentObserver
import android.os.Build
import android.provider.Settings
import android.service.quicksettings.Tile
import com.zacharee1.systemuituner.util.ImmersiveManager
import com.zacharee1.systemuituner.util.safeUpdateTile
import kotlinx.coroutines.launch

@TargetApi(Build.VERSION_CODES.N)
abstract class BaseImmersiveTile : CoroutineTileService() {
    protected abstract val type: ImmersiveManager.ImmersiveMode

    private val immersiveManager by lazy { ImmersiveManager(this) }
    private val observer = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {
            updateState()
        }
