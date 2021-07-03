
package com.zacharee1.systemuituner.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.zacharee1.systemuituner.IManager
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

//TODO: something weird is going on here where some settings are overridden incorrectly when first enabled as persistent.
//TODO: Figure it out?
class Manager : Service(), SharedPreferences.OnSharedPreferenceChangeListener, CoroutineScope by MainScope() {
    companion object {
        const val NOTIFICATION_CHANNEL_ID = "manager_service"
    }

    private val observer = Observer()

    override fun onBind(intent: Intent?): IBinder {
        return ManagerImpl()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            PrefManager.PERSISTENT_OPTIONS -> {
                doInitialCheck()
                observer.register()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        prefManager.prefs.registerOnSharedPreferenceChangeListener(this)
        try {
            doInitialCheck()
        } catch (_: IllegalStateException) {}
        observer.register()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    resources.getString(R.string.manager_channel),
                    NotificationManager.IMPORTANCE_LOW
                )
            )
