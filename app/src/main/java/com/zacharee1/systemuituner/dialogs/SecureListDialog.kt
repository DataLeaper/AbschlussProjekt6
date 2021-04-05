package com.zacharee1.systemuituner.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.interfaces.IListPreference
import kotlinx.coroutines.launch

class SecureListDialog : BaseOptionDialog() {
    companion object {
        fun newInstance(key: String): SecureListDialog {
            return SecureListDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_KEY, key)
                }
            }
        }
    }

    private val listPref: IListPreference
 