package com.zacharee1.systemuituner.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.preference.*
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.interfaces.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@SuppressLint("RestrictedApi")
class SearchIndex private constructor(context: Context) : ContextWrapper(context), CoroutineScope by MainScope() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: SearchIndex? = null

        val toInflate = arrayOf(
            R.xml.prefs_apps to R.id.appsFragment,
            R.xml.prefs_audio to R.id.audioFragment,
            R.xml.prefs_developer to R.id.developerFragment,
            R.xml.prefs_display to R.id.displayFragment,
            R.xml.prefs_net_cellular to R.id.netCellFragment,
            R.xml.prefs_net_misc to R.id.netMiscellaneousFragment,
            R.xml.prefs_net_wifi to R.id.netWiFiFragment,
            R.xml.prefs_notifications to R.id.notificationsFragment,
            R.xml.prefs_status_bar to R.id.statusBarFragment,
            R.xml.prefs_storage to R.id.storageFragment,
            R.xml.prefs_ui to R.id.UIFra