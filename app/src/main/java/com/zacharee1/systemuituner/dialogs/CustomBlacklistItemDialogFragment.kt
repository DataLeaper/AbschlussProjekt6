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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = ScrolledRoundedBottomSheetDialog(requireContext())

        builder.findViewById<View>(android.R.id.content)?.let { onBindDialogView(it) }
        builder.setTitle(preference.title)
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            this.dialog?.findViewById<View>(android.R.id.content)?.apply {
                val label = findViewById<TextInputLayout>(R.id.label).editText?.text?.toString()
                val key = findViewById<TextInputLayout>(R.id.key).editText?.text?.toString() ?: return@apply

                if (key.isBlank()) return@apply

                val item = CustomBlacklistItemInfo(label, key)

                context.prefManager.apply {
                    customBlacklistItems = customBlacklistItems.apply {
                  