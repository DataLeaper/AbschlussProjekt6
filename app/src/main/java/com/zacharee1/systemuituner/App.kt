package com.zacharee1.systemuituner

import android.app.ActivityManager
import android.app.ActivityThread
import android.app.Application
import android.app.ApplicationErrorReport.ParcelableCrashInfo
import android.app.IApplicationThread
import android.content.*
import android.os.*
import android.util.AndroidRuntimeException
import android.util.Log
import androidx.core.content.ContextCompat
import com.bugsnag.android.Bugsnag
import com.zacharee1.systemuituner.services.Manager
import com.zacharee1.systemuituner.util.PersistenceHandlerRegistry
import com.zacharee1.systemuituner.util.PrefManager
import com.zacharee1.systemuituner.util.prefManager
import org.lsposed.hiddenapibypass.HiddenApiBypass
import kotlin.system.exitProcess

class App : Application(), SharedPreferences.OnSharedPreferenceChangeListener {
    companion object {
        private val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {}
            override fun onServiceDisconnected(name: ComponentName?) {}
        }

        fun updateServiceState(context: Context) {
            with (context) {
                if (prefManager.persistentOptions.isEmpty()) {
                    try {
                        unbindService(connection)
                    } catch (_: IllegalArgumentException) {}
                    stopService(Intent(this, Manager::class.java))
                } else {
                    try {
                        bindService(Intent(this, Manager::class.java), connection, Context.BIND_AUTO_CREATE)
                    } catch (e: Exception) {
                        Log.e("SystemUITuner", "Unable to bind service. Build SDK ${Build.VERSION.SDK_INT}.", e)
                        Bugsnag.getClient().notify(Exception("Unable to bind service. Build SDK ${Build.VERSION.SDK_INT}", e))

                        try {
                            ContextCompat.startForegroundService(context, Intent(this, Manager::class.java))
                        } catch (e: Exception) {
                            Log.e("SystemUITuner", "Unable to start service. Build SDK ${Bu