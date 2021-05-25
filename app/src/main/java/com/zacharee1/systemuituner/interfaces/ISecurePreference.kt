package com.zacharee1.systemuituner.interfaces

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.SettingsType

interface ISecurePreference : IDangerousPreference {
    var type: SettingsType
    var writeKey: String

    fun initSecure(pref: Preference) {
        if (writeKey.isBlank()) writeKey = pref.key ?: ""
    }
}

class SecurePreference(context: Context, attrs: AttributeSet?) :
    ISecurePreference, IDangerousPreference by DangerousPreference(context, attrs) {
    override var type: SettingsType =
        SettingsType.UNDEFINED
    override var writeKey: String = ""

