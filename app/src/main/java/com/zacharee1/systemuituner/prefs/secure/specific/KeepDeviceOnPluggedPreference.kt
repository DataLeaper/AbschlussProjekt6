
package com.zacharee1.systemuituner.prefs.secure.specific

import android.content.Context
import android.provider.Settings
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.interfaces.ISpecificPreference
import com.zacharee1.systemuituner.prefs.base.BaseDialogPreference
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.writeSetting

class KeepDeviceOnPluggedPreference(context: Context, attrs: AttributeSet) : BaseDialogPreference(context, attrs),
    ISpecificPreference {
    override val keys = hashMapOf(
        SettingsType.GLOBAL to arrayOf(Settings.Global.STAY_ON_WHILE_PLUGGED_IN)
    )

    init {
        key = "keep_device_on"

        setTitle(R.string.feature_keep_screen_on)
        setSummary(R.string.feature_keep_screen_on_desc)

        dialogTitle = title
        dialogMessage = summary
        setIcon(R.drawable.ic_baseline_visibility_24)
        iconColor = ContextCompat.getColor(context, R.color.pref_color_2)
    }

    override suspend fun onValueChanged(newValue: Any?, key: String): Boolean {
        newValue as Int

        return context.writeSetting(SettingsType.GLOBAL, Settings.Global.STAY_ON_WHILE_PLUGGED_IN, newValue, saveOption = true)
    }
}