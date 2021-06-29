package com.zacharee1.systemuituner.prefs.secure.specific

import android.content.Context
import android.provider.Settings
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.interfaces.ISpecificPreference
import com.zacharee1.systemuituner.prefs.base.BaseDialogPreference
import com.zacharee1.systemuituner.data.SettingsType

class ImmersiveModePreference(context: Context, attrs: AttributeSet) : BaseDialogPreference(context, attrs),
    ISpecificPreference {
    override val keys = hashMapOf(
        SettingsType.GLOBAL to arrayOf(Settings.Global.POLICY_CONTROL)
    )

    init {
        key = "immersive_mode_pref"

        setTitle(R.string.feature_immersive_mode)
        setSummary(R.string.feature_immersive_mode_desc)

        dialogTitle = title
        dialogMessage = summary
        setIcon(R.drawable.ic_baseline_fullscreen_24)
        iconColor = ContextCompat.getColor(context, R.color.pref_color_4)
    }
}