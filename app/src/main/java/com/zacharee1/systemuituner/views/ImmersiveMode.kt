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
        list.adapter = ImmersiveAdapter(immersiveInfo, immersiveManager)

        rootView.findViewById<Button>(R.id.negative_button).apply {
            isVisible = true
            setText(R.string.reset)
            setOnClickListener {
                immersiveInfo.clear()
                launch {
                    immersiveManager.setAdvancedImmersive(immersiveInfo)
                    list.adapter?.notifyItemRangeChanged(0, list.adapter?.itemCount ?: 0)
                }
            }
        }
    }

    inner class ImmersiveAdapter(
        private val immInfo: ImmersiveManager.ImmersiveInfo,
        private val manager: ImmersiveManager
    ) : RecyclerView.Adapter<ImmersiveAdapter.VH>() {
        private val items = arrayListOf(
            ItemInfo(
                R.string.immersive_full,
                ImmersiveManager.ImmersiveMode.FULL
            ),
            ItemInfo(
                R.string.immersive_status,
                ImmersiveManager.ImmersiveMode.STATUS
            ),
            ItemInfo(
                R.string.immersive_nav,
                ImmersiveManager.ImmersiveMode.NAV
            )
        )

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return VH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.immersive_mode_item,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val info = items[position]
            val binding = ImmersiveModeItemBinding.bind(holder.itemView)

            holder.itemView.apply {
                binding.immersiveName.text = resources.getText(info.name)
                binding.all.isChecked = when (info.type) {
                    ImmersiveManager.ImmersiveMode.FULL -> immInfo.allFull
                    ImmersiveManager.ImmersiveMode.STATUS -> immInfo.allStatus
                    ImmersiveManager.ImmersiveMode.NAV -> immInfo.allNav
                    else -> false
                }
                binding.whitelistButton.isEnabled = !binding.all.isChecked

                binding.all.setOnClickListener {
                    val newInfo = items[holder.bindingAdapterPosition]
                    binding.all.isChecked = !binding.all.isChecked

                    when (newInfo.type) {
                  