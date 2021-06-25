package com.zacharee1.systemuituner.prefs.secure.specific

import android.content.Context
import android.provider.Settings
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.AnimationScalesData
import com.zacharee1.systemuituner.interfaces.ISpecificPreference
import com.zacharee1.systemuituner.prefs.base.BaseDialogPreference
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.SettingsInfo
import com.zacharee1.systemuituner.util.writeSettingsBulk

class AnimationScalesPreference(context: Context, attrs: AttributeSet) : BaseDialogPreference(context, attrs),
    ISpecificPreference {
    override val keys = hashMapOf(
        SettingsType.GLOBAL to arrayOf(
            Settings.Global.ANIMATOR_DURATION_SCALE,
            Settings.Global.TRANSI