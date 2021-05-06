package com.zacharee1.systemuituner.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.preference.*
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.CustomPersistentOption
import com.zacharee1.systemuituner.data.PersistentOption
import com.zacharee1.systemuituner.data.SearchIndex
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.databinding.CustomPersistentOptionWidgetBinding
import com.zacharee1.systemuituner.dialogs.CustomPersistentOptionDialogFragment
import com.zacharee1.systemuituner.dialogs.RoundedBottomSheetDialog
import com.zacharee1.systemuituner.interfaces.*
import com.zacharee1.systemuituner.prefs.InlineActivityPreference
import com.zacharee1.systemuituner.util.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class PersistentFragment : BasePrefFragment(), SearchView.OnQueryTextListener, SharedPreferences.OnSharedPreferenceChangeListener {
    override val widgetLayout: Int = Int.MIN_VALUE

    private var currentQuery: String? = null

    private val preferences = ArrayList<PersistentPreference>()
    private val isLoaded = async {
        SearchIndex.toInflate.forEach {
            inflate(it.first)
        }

//        preferences.addAll(requireContext().prefManager.customPersistentOptions.map {
//            PersistentPreference.fromCustomPersistentOption(requireConte