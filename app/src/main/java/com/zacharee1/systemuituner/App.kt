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
                            Log.e("SystemUITuner", "Unable to start service. Build SDK ${Build.VERSION.SDK_INT}.", e)
                            Bugsnag.getClient().notify(Exception("Unable to start service. Build SDK ${Build.VERSION.SDK_INT}", e))
                        }
                    }
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (prefManager.enableCrashReports == true) {
            Bugsnag.start(this)
        }

        initExceptionHandler()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HiddenApiBypass.setHiddenApiExemptions("L")
        }

        PersistenceHandlerRegistry.register(this)

        updateServiceState(this)
        prefManager.prefs.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            PrefManager.PERSISTENT_OPTIONS -> {
                updateServiceState(this)
            }
        }
    }

    private fun initExceptionHandler() {
        val previousHandler = Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(previousHandler))
    }

    class ExceptionHandler(private val previousHandler: Thread.UncaughtExceptionHandler?) : Thread.UncaughtExceptionHandler {
        private var crashing = false

        override fun uncaughtException(t: Thread, e: Throwable) {
            when {
                e is AndroidRuntimeException -> {
                    Log.e("SystemUITuner", "Caught a runtime Exception!", e)
                    Bugsnag.getClient().notify(e)
                    Looper.loop()
                }
                e is SecurityException &&
                        e.message?.contains(
                            "nor current process has android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS",
                            true
                        ) == true -> {
                    Log.e("SystemUITuner", "Google Play Services error!", e)
                    Bugsnag.getClient().notify(e)
                    Looper.loop()
                }
                e.hasDeadObjectCause -> {
                    if (!crashing) {
                        crashing = true

                        // Try to end profiling. If a profiler is running at this point, and we kill the
                        // process (below), the in-memory buffer will be lost. So try to stop, wh