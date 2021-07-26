package com.zacharee1.systemuituner.util.verifiers

import android.content.Context
import com.zacharee1.systemuituner.util.hasSdCard

abstract class BaseListPreferenceVerifier(internal val context: Context) {
    abstract fun verifyEntries(
        entries: Array<CharSequence?>?,
        values: Array<CharSequence?>?
    ): Pair<Array<CharSequence?>?, Array<CharSequence?>?>
}

class StorageVerifier(context: Context) : BaseListPreferenceVerifier(context) {
    override fun verifyEntries(
        entries: Array<CharSequence?>?,
        values: Array<CharSequence?>?
    ): Pair<Array<CharSequence?>?, Array<CharSequence?>?> {
        if (entries == null |