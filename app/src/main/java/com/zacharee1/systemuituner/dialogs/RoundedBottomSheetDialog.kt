
package com.zacharee1.systemuituner.dialogs

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.databinding.BaseDialogLayoutBinding
import com.zacharee1.systemuituner.util.activityContext

open class RoundedBottomSheetDialog(context: Context) : BottomSheetDialog(context, R.style.BottomSheetTheme), View.OnClickListener {
    private var positiveListener: DialogInterface.OnClickListener? = null
    private var negativeListener: DialogInterface.OnClickListener? = null

    val baseBinding = BaseDialogLayoutBinding.inflate(layoutInflater)

    init {
        setContentView(baseBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseBinding.positiveButton.setOnClickListener(this)
        baseBinding.negativeButton.setOnClickListener(this)

        updateSize()

        var oldOrientation = context.resources.configuration.orientation

        baseBinding.listener.onConfigurationChangedListener = { newConfig ->
            if (newConfig.orientation != oldOrientation) {
                oldOrientation = newConfig.orientation

                updateSize()
            }
        }
    }

    override fun onAttachedToWindow() {
        findViewById<View>(android.R.id.content)?.apply {
            (context.activityContext as? ComponentActivity)?.let { activity ->
                setViewTreeLifecycleOwner(activity)
                setViewTreeSavedStateRegistryOwner(activity)
            }
        }

        super.onAttachedToWindow()
    }

    private fun updateSize() {
        val maxWidth = context.resources.getDimensionPixelSize(R.dimen.max_bottom_sheet_width)
        val screenWidth = context.resources.displayMetrics.widthPixels

        window?.setLayout(if (screenWidth > maxWidth) maxWidth else ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)

        findViewById<TextView>(android.R.id.title)?.setText(titleId)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)

        findViewById<TextView>(android.R.id.title)?.text = title
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.positive_button -> {
                if (positiveListener != null) {
                    positiveListener?.onClick(this, DialogInterface.BUTTON_POSITIVE)
                } else {
                    dismiss()
                }
            }

            R.id.negative_button -> {
                if (negativeListener != null) {
                    negativeListener?.onClick(this, DialogInterface.BUTTON_NEGATIVE)
                } else {
                    dismiss()
                }
            }
        }
    }

    fun setIcon(icon: Int) {
        setIcon(ContextCompat.getDrawable(context, icon))
    }

    fun setIcon(icon: Drawable?) {
        baseBinding.icon.setImageDrawable(icon)
    }

    fun setPositiveButton(text: Int, listener: DialogInterface.OnClickListener?) {
        setPositiveButton(context.resources.getText(text), listener)
    }

    fun setPositiveButton(text: CharSequence?, listener: DialogInterface.OnClickListener?) {
        baseBinding.positiveButton.text = text
        baseBinding.positiveButton.isVisible = text != null

        positiveListener = listener
    }

    fun setNegativeButton(text: Int, listener: DialogInterface.OnClickListener?) {
        setNegativeButton(context.resources.getText(text), listener)
    }

    fun setNegativeButton(text: CharSequence?, listener: DialogInterface.OnClickListener?) {
        baseBinding.negativeButton.text = text
        baseBinding.negativeButton.isVisible = text != null

        negativeListener = listener
    }

    fun setMessage(msg: Int) {
        setMessage(if (msg == 0) null else context.getText(msg))
    }

    fun setMessage(msg: CharSequence?) {
        findViewById<TextView>(android.R.id.message)?.apply {
            isVisible = msg != null
            text = msg
        }
    }

    open fun setLayout(layout: Int) {
        setLayout(if (layout == 0) null else LayoutInflater.from(context).inflate(layout, null))
    }

    open fun setLayout(layout: View?) {
        findViewById<ViewGroup>(R.id.content_wrapper)?.apply {
            isVisible = layout != null
            removeAllViews()
            if (layout != null) {
                addView(layout)
            }
        }
    }
}