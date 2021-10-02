package com.zacharee1.systemuituner.views

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.databinding.TouchwizNavigationBarColorDialogBinding
import com.zacharee1.systemuituner.interfaces.IOptionDialogCallback
import com.zacharee1.systemuituner.util.*

class TouchWizNavigationBarColorView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), ColorPickerDialogListener, IOptionDialogCallback {
    override var callback: (suspend (data: Any?) -> Boolean)? = null

    private val dialog: ColorPickerDialog = ColorPickerDialog.newBuilder()
        .setDialogType(ColorPickerDialog.TYPE_PRESETS)
        .setDialogTitle(R.string.option_touchwiz_navbar_color)
        .setColorShape(ColorShape.CIRCLE