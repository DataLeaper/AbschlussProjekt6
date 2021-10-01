package com.zacharee1.systemuituner.views

import android.content.Context
import android.provider.Settings
import android.util.AttributeSet
import android.widget.ScrollView
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.databinding.StorageThresholdsBinding
import com.zacharee1.systemuituner.util.SimpleSeekBarListener
import com.zacharee1.systemuituner.util.getSetting
import com.zacharee1.systemuituner.util.launch
import com.zacharee1.systemuituner.util.writeSetting

class StorageThresholds(context: Context, attrs: AttributeSet) : ScrollView(context, attrs) {
    privat