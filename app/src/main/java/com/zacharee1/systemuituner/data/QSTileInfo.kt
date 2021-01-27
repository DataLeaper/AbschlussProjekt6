
package com.zacharee1.systemuituner.data

import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.util.capitalized
import com.zacharee1.systemuituner.util.getApplicationInfoCompat
import com.zacharee1.systemuituner.util.getServiceInfoCompat
import java.util.regex.Pattern

class QSTileInfo(
    val key: String
) {
    enum class Type {
        INTENT,
        CUSTOM,
        STANDARD
    }

    private var _label: String? = null
    private var _icon: Drawable? = null

    val type: Type = when {
        key.contains("intent(") -> Type.INTENT
        key.contains("custom(") -> Type.CUSTOM
        else -> Type.STANDARD
    }

    fun getLabel(context: Context): String {
        return _label ?: when (type) {
            Type.INTENT -> getIntentLabel()
            Type.CUSTOM -> context.getCustomLabel()
            Type.STANDARD -> key.capitalized
        }.also { _label = it }
    }

    fun getIcon(context: Context): Drawable? {
        return _icon ?: when (type) {
            Type.INTENT -> context.getDefaultDrawable()
            Type.CUSTOM -> context.getCustomDrawable()
            Type.STANDARD -> context.chooseStandardDrawable()
        }.also { _icon = it }
    }

    private fun getIntentLabel(): String {
        val p = Pattern.compile("\\((.*?)\\)")
        val m = p.matcher(key)

        var title = ""

        while (!m.hitEnd()) {
            if (m.find()) title = m.group()
        }

        return title.replace("(", "").replace(")", "")
    }

    private fun Context.getCustomLabel(): String {
        val component = getNameAndComponentForCustom()

        return try {
            if (component != null) {
                packageManager.getServiceInfoCompat(component).loadLabel(packageManager)
                    .toString()
            } else {
                packageManager.getApplicationInfoCompat(packageName).loadLabel(packageManager)
                    .toString()
            }
        } catch (e: Exception) {
            try {
                component?.className?.split(".")?.run { this[size - 1] } ?: packageName
            } catch (e: Exception) {
                packageName