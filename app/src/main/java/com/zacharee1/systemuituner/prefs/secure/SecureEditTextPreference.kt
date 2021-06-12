package com.zacharee1.systemuituner.prefs.secure

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.prefs.secure.base.BaseSecurePreference
import com.zacharee1.systemuituner.util.getSetting

class SecureEditTextPreference(context: Context, attrs: AttributeSet) : BaseSecurePreference(context, attrs) {
    var inputType: Int = InputType.TYPE_CLASS_TEXT

    var text: CharSequence? = null

    init {
        val array = co