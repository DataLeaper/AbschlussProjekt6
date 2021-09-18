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

        ac.isChecked = current and BatteryManager.BATTERY_PLUGGED_AC != 0
        usb.isChecked = current and BatteryManager.BATTERY_PLUGGED_USB != 0
        wireless.isChecked = current and BatteryManager.BATTERY_PLUGGED_WIRELESS != 0

        var latestState: Int = current

        val listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val result = when (buttonView) {
                ac -> (if (isChecked) BatteryManager.BATTERY_PLUGGED_AC else 0) or
                        (if (usb.isChecked) BatteryManager.BATTERY_PLUGGED_USB else 0) or
                        (if (wireless.isChecked) BatteryManager.BATTERY_PLUGGED_WIRELESS else 0)
                usb -> (if (ac.isChecked) BatteryManager.BATTERY_PLUGGED_AC else 0) or
                        (if (isChecked) BatteryManager.BATTERY_PLUGGED_USB else 0) or
                        (if (wireless.isChecked) BatteryManager.BATTERY_PLUGGED_WIRELESS else 0)
                wireless -> (if (ac.isChecked) BatteryManager.BATTERY_PLUGGED_AC else 0) or
                        (if (usb.isChecked) BatteryManager.BATTERY_PLUGGED_USB else 0) or
                        (if (isChecked) BatteryManager.BATTERY_PLUGGED_WIRELESS else 0)
                else -> current
            }

            if (latestState != result) {
                launch {
                    if (callback?.invoke(result) == false) {
                        ac.isChecked = latestState and BatteryManager.BATTERY_PLUGGED_AC != 0
                        usb.isChecked = latestState and BatteryManager.BATTERY_PLUGGED_USB != 0
                        wireless.isChecked = latestState and BatteryManager.BATTERY_PLUGGED_W