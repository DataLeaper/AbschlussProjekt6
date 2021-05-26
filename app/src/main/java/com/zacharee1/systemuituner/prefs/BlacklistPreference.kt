package com.zacharee1.systemuituner.prefs

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.preference.PreferenceViewHolder
import androidx.preference.SwitchPreference
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.getSetting

open class BlacklistPreference(context: Context, attrs: AttributeSet?) : SwitchPreference(context, attrs) {