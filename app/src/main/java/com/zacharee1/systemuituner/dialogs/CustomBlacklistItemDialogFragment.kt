package com.zacharee1.systemuituner.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceDialogFragmentCompat
import com.google.android.material.textfield.TextInputLayout
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.CustomBlacklistItemInfo
import com.zacharee1.systemuituner.util.prefManager

class CustomBlacklistItemDialogFragment : PreferenceDialogFragmentCompat() {
    companion object {
        fun newInstance(key: String): CustomBlacklistItemDialogFragment {
            val instance = CustomBlacklistItemDialogFragment()
            val args = Bundle()

            args.putString(BaseOptionDialog.ARG_KEY, key)

            instance.arguments = args

            return instance
        }
    }

    override fun onCreate