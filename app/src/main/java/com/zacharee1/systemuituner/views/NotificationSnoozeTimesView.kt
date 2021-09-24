package com.zacharee1.systemuituner.views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.FrameLayout
import com.zacharee1.systemuituner.databinding.NotificationSnoozeTimesBinding
import com.zacharee1.systemuituner.interfaces.IOptionDialogCallback
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.getSetting
import com.zacharee1.systemuituner.util.launch

class NotificationSnoozeTimesView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), IOptionDialogCallback {
    override var callback: (suspend (data: Any?) -> Boolean)? = null

    private val binding by lazy { NotificationSnoozeTimesBinding.bind(this) }

    override fun onFinishInflate() {
        super.onFinishInflate()

        init()
    }

    private fun init() {
        val setting = context.getSetting(SettingsType.GLOBAL, "notification_snooze_options")

        var defTime = "60"
        var aTime = "15"
        var bTime = "30"
        var cTime = "60"
        var dTime = "120"

        if (!setting.isNullOrBlank()) {
            try {
                val parts = setting.split(","