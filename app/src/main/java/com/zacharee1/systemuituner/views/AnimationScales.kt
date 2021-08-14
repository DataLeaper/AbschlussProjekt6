package com.zacharee1.systemuituner.views

import android.content.Context
import android.provider.Settings
import android.util.AttributeSet
import android.widget.ScrollView
import com.zacharee1.systemuituner.data.AnimationScalesData
import com.zacharee1.systemuituner.databinding.AnimationDialogBinding
import com.zacharee1.systemuituner.interfaces.IOptionDialogCallback
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.getSetting
import com.zacharee1.systemuituner.util.launch
import tk.zwander.seekbarpreference.SeekBarView

class AnimationScales(context: Context, attrs: AttributeSet) : ScrollView(context, attrs), IOptionDialogCallback {
    override var callback: (suspend (data: Any?) -> Boolean)? = null
    private val scaleData = AnimationScalesData()

    private val binding by lazy { AnimationDialogBinding.bind(this) }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val initialAnimatorScale = context.getSetting(SettingsType.GLOBAL, Settings.Global.ANIMATOR_DURATION_SCALE, 1f)?.toFloatOrNul