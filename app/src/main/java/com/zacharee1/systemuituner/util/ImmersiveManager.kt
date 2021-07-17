package com.zacharee1.systemuituner.util

import android.content.Context
import android.content.ContextWrapper
import android.provider.Settings
import com.zacharee1.systemuituner.data.SettingsType

class ImmersiveManager(context: Context) : ContextWrapper(context) {
    enum class ImmersiveMode(val type: String) {
        NONE("immersive.none"),
        STATUS("immersive.status"),
        NAV("immersive.navigation"),
        FULL("immersive.full")
    }

    data class ImmersiveInfo(
        var allFull: Boolean = false,
        var allStatus: Boolean = false,
        var allNav: Boolean = false,
        val fullApps: ArrayList<String> = ArrayList(),
        val fullBl: ArrayList<String> = ArrayList(),
        val statusApps: ArrayList<String> = ArrayList(),
        val statusBl: ArrayList<String> = ArrayList(),
        val navApps: ArrayList<String> = ArrayList(),
        val navBl: ArrayList<String> = ArrayList()
    ) {
        fun clear() {
            allFull = false
            allStatus = false
            allNav = false
            fullApps.clear()
            statusApps.clear()
            navApps.clear()
            fullBl.clear()
            statusBl.clear()
            navBl.clear()
        }
    }

    suspend fun setAdvancedImmersive(info: ImmersiveInfo) {
        val modes = ArrayList<String?>()

        val fullMode = buildModeString(ImmersiveMode.FULL.type, info.allFull, info.fullApps, info.fullBl)
        val statusMode = buildModeString(ImmersiveMode.STATUS.type, info.allStatus, info.statusApps, info.statusBl)
        val navMode = buildModeString(ImmersiveMode.NAV.type, info.allNav, info.navApps, info.navBl)

        if (fullMode.isNotBlank()) modes.add(fullMode)
        if (statusMode.isNotBlank()) modes.add(statusMode)
        if (navMode.isNotBlank()) modes.add(navMode)

    