package com.zacharee1.systemuituner.util.verifiers

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.util.hasSdCard
import com.zacharee1.systemuituner.util.isTouchWiz

abstract class BasePreferenceEnabledVerifier(internal val context: Context) {
    abstract val shouldBeEnabled: Boolean
    abstract val message: CharSequence?
}

class EnableStorage(context: Context) : BasePreferenceEnabledVerifier(context) {
    override val shouldBeEnabled: Boolean
        get() = context.hasSdCard
    override val message: CharSequence?
        get() = context.resources.getString(R.string.compatibility_message_storage)
}

class EnableLockscreenShortcuts(context: Context) : BasePreferenceEnabledVerifier(context) {
    override val shouldBeEnabled: Boolean
        @SuppressLint("DiscouragedApi")
        get() {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) return true
            if (context.isTouchWiz) return true

            val resNames = arrayOf("config