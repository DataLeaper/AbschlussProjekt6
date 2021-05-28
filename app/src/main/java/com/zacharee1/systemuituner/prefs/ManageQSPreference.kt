package com.zacharee1.systemuituner.prefs

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.AttributeSet
import androidx.preference.Preference
import androidx.preference.SwitchPreference
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.interfaces.DangerousPreference
import com.zacharee1.systemuituner.interfaces.IDangerousPreference
import com.zacharee1.systemuituner.util.isComponentEnabled

open class ManageQSPreference(context: Context, attrs: AttributeSet?) : SwitchPreference(context, attrs),
    Preference.OnPreferenceChangeListener,
    IDangerousPreference by DangerousPreference(context, attrs) {

    private var manageComponent: ComponentName? = null

    init {
        isPersistent = false
        layoutResource = R.layout.custom_pre