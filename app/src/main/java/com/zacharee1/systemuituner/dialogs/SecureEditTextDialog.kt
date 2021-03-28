package com.zacharee1.systemuituner.dialogs

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.prefs.secure.SecureEditTextPreference
import com.zacharee1.systemuituner.util.defaultValue
import kotlinx.coroutines.launch

class SecureEditTextDialog : BaseOptionDialog() {
    private var editText: EditText? = null
    private var text: CharSequence? = null

    override val layoutRes: Int
        get() = preference.dialogLayoutResource

    private val editTextPreference: SecureEditTextPreference
        get() = preference as SecureEditTextPreference

    companion object {
        private const val SAVE_STATE_TEXT = "EditTextPreferenceDialogFragment.text"

        