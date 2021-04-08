package com.zacharee1.systemuituner.dialogs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.zacharee1.systemuituner.R

class SupportersDialog(context: Context) : ScrolledRoundedBottomSheetDialog(context) {
    @SuppressLint("InflateParams")
    val view: View = LayoutInflater.from(context).inflate(R.layout.supporters_dialog, null)
    init {
        setTitle(R.string.su