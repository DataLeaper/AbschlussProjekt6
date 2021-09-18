package com.zacharee1.systemuituner.views

import android.content.Context
import android.os.BatteryManager
import android.provider.Settings
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.ScrollView
import com.zacharee1.systemuituner.databinding.KeepDevicePluggedDialogBinding
import com.zacharee1.systemuituner.interfaces.IOptionDialogCallback
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.getSetting
import com.zacharee1.systemuituner.util.launch

class KeepOnPlugged(context: Context, attrs: AttributeSet) : ScrollView(context, attrs), IOptionDialogCallback {
    override var callback: (suspend (data: Any?) -> Boolean)? = null

    private val binding by lazy { KeepDevicePluggedDialogBinding.bind(this) }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val ac = binding.onAc
        val usb = binding.onUsb
        val wireless = binding.onWireless
        val current = context.getSetting(
            SettingsType.GLOBAL,
            Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
            0
        )?.toIntOrNull() ?: 0

        ac.isChecked = current and BatteryManager.BATTERY_PLU