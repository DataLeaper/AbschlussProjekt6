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
   