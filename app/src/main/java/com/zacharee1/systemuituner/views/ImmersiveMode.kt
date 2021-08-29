package com.zacharee1.systemuituner.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.zacharee1.systemuituner.IImmersiveSelectionCallback
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.activities.ImmersiveListSelector
import com.zacharee1.systemuituner.databinding.ImmersiveModeBinding
import com.zacharee1.systemuituner.databinding.ImmersiveModeItemBinding
import com.zacharee1.systemuituner.util.ImmersiveManager
import com.zacharee1.systemuituner.util.launch
import com.zacharee1.systemuituner.util.prefManager

class ImmersiveMode(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val immersiveManager = ImmersiveManager(context)
    private val immersiveInfo = immersiveManager.parseAdvancedImmersive().apply {
        immersiveManager.loadInSavedLists(this)
    }

    private val binding by lazy { ImmersiveModeBinding.bind(this) }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val list = binding.modeList
        list.adapter = ImmersiveAdapter(immersiveInfo, immersi