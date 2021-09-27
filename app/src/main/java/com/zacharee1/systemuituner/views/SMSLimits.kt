package com.zacharee1.systemuituner.views

import android.content.Context
import android.provider.Settings
import android.util.AttributeSet
import android.widget.ScrollView
import androidx.lifecycle.lifecycleScope
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.databinding.SmsLimitsBinding
import com.zacharee1.systemuituner.util.*
import kotlinx.coroutines.launch

class SMSLimits(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    companion object {
        private const val COUNT_DEF = 30
        private const val INTERVAL_DEF = 1800000
    }

    private val binding by lazy { SmsLimitsBinding.bind(this) }

    override fun onFinishInflate() {
        super.onFinishInflate()

        binding.maxCount.editText?.setText(context.getSetting(SettingsType.GLOBAL, Settings.Global.SMS_OUTGOING_CHECK_MAX_COUNT, COUNT_DEF))
        binding.interval.editText?.setText(context.getSetting(SettingsType.GLOBAL, Settings.Global.SMS_OUTGOING_CHECK_INTERVAL_MS, INTERVAL_DEF))

        binding.maxCount.setStartIconOnClickListener {
            launch {
                context.writeSetting(SettingsType.GLOBAL, Settings.Global.SMS_OUTGOING_CHECK_MAX_COUNT, COUNT_DEF, saveOption = true)
       