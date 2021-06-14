package com.zacharee1.systemuituner.prefs.secure

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import androidx.core.content.edit
import androidx.core.content.res.TypedArrayUtils
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.prefs.secure.base.BaseSecurePreference
import com.zacharee1.systemuituner.interfaces.IListPreference
import com.zacharee1.systemuituner.util.getSetting
import com.zacharee1.systemuituner.util.verifiers.BaseListPreferenceVerifier

@SuppressLint("RestrictedApi")
class SecureListPreference(context: Context, attrs: AttributeSet) : BaseSecurePreference(context, attrs),
    IListPreference {
    private var verifier: BaseListPreferenceVerifier? = null

    private var setValue: Boolean = false
    override var value: String? = null
        set(value) {
            // Always persist/notify the first time.
            val changed = !TextUtils.equals(field, value)
            if (changed || !setValue) {
                field = value
                setValue = true
                try {
                    persistString(value)
                } catch (e: ClassCastException) {
                    