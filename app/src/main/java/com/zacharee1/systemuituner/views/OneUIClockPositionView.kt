package com.zacharee1.systemuituner.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.databinding.OneUiClockPositionBinding
import com.zacharee1.systemuituner.interfaces.IOptionDialogCallback
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.getSetting
import com.zacharee1.systemuituner.util.launch

class OneUIClockPositionView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs), IOptionDialogCallback {
    override var callback: (suspend (data: Any?) -> Boolean)? = null

    companion object {
        const val POSITION_LEFT = "left_clock_position"
        const val POSITION_MIDDLE = "middle_clock_position"
        const val POSITION_RIGHT = "right_clock_position"
    }

    private val binding by lazy { OneUiClockPositionBinding.bind(this) }

    override fun onFinishInflate() {
        super.onFinishInflate()

        init()
    }

    private fun init() {
        val blacklist = context.getSetting(SettingsType.SECURE, "icon_blacklist") ?: ""
        val currentPosition = when {
            blacklist.contains(POSITION_RIGHT) -> R.id.position_right
            blacklist.contains(POSITION_MIDDLE) -> R.id.position_middle
            else -> R.id.position_left
        }

        binding.clockPo