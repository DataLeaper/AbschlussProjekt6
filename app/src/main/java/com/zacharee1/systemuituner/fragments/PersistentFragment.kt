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
//            PersistentPreference.fromCustomPersistentOption(requireContext(), it)
//        })
        true
    }

    private val persistentCategory: PreferenceCategory?
        get() = preferenceScreen.findPreference("prefs_group")
    private val noticeCategory: PreferenceCategory?
        get() = preferenceScreen.findPreference("notices")

    @SuppressLint("RestrictedApi")
    private fun inflate(resource: Int): PreferenceScreen {
        return preferenceManager.inflateFromResource(requireContext(), resource, null).also { process(it) }
    }

    private fun process(group: PreferenceGroup) {
        for (i in 0 until group.preferenceCount) {
            val child = group.getPreference(i)

            if (child is PreferenceGroup) process(child)
            else {
                if (child is INoPersistPreference) continue
                preferences.add(PersistentPreference.fromPreference(false, child, this))
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs_search, rootKey)

        addNoticePreferences()
        filterPersistent(null) {
            it.forEach { pref ->
                persistentCategory?.addPreference(construct(pref))
            }
        }

        persistentCategory?.isOrderingAsAdded = false
        requireContext().prefManager.prefs.registerOnSharedPreferenceChangeListener(this)
        onQueryTextChange(null)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == PrefManager.CUSTOM_PERSISTENT_OPTIONS) {
            onQueryTextChange(currentQuery)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.background = ContextCompat.getDrawable(requireContext(), R.drawable.search_bg)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        currentQuery = newText

        noticeCategory?.isVisible = newText == null

        filterPersistent(newText) {
            val toRemove = ArrayList<Preference>()

            persistentCategory?.forEach { child ->
                if (!it.map { c -> c.key }.contains(child.key)) {
                    toRemove.add(child)
                }
            }

            toRemove.forEach { pref ->
                persistentCategory?.removePreference(pref)
            }

            it.forEach { pref ->
                if (persistentCategory?.hasPreference(pref.key) == false) {
                    persistentCategory?.addPreference(construct(pref))
                }
            }
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()

        requireContext().prefManager.prefs.unregisterOnSharedPreferenceChangeListener(this)
    }

    fun addOrEditCustomItem(label: String? = null, key: String? = null, value: String? = null, type: SettingsType? = null) {
        val fragment = if (type == null || key == null || label == null) CustomPersistentOptionDialogFragment()
        else CustomPersistentOptionDialogFragment.forEdit(
            label, key, value, type
        )
        @Suppress("DEPRECATION")
        fragment.setTargetFragment(this, 0)
        fragment.show(parentFragmentManager, null)
    }

    private fun filterPersistent(query: String?, result: (ArrayList<PersistentPreference>) -> Unit) = launch {
        isLoaded.await()

        val filter = async {
            ArrayList(
                preferences.filter {
                    (query.isNullOrBlank() ||
                            it.title.toString().contains(query, true) ||
                            it.origSummary?.toString()?.contains(query, true) == true)
                }.map { PersistentPreference.fromPreference(false, it, this@PersistentFragment) } +
                        requireContext().prefManager.customPersistentOptions.filter {
                            query.isNullOrBlank() ||
                                    it.label.contains(query, true) ||
                                    it.key.contains(query, true)
                        }.map {
                            PersistentPreference.fromCustomPersistentOption(this@PersistentFragment, it)
                        }
            )
        }

        result(filter.await())
    }

    private fun addNoticePreferences() {
        noticeCategory?.addPreference(
            InlineActivityPreference(
                requireContext(),
                Intent(Intent.ACTION_VIEW, Uri.parse("https://dontkillmyapp.com"))
            ).apply {
                title = resources.getString(R.string.persistent_options_not_sticking_title)
                summary = resources.getString(R.string.persistent_options_not_sticking_desc)
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_help_outline_24)
            }
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !Settings.canDrawOverlays(requireContext())) {
            noticeCategory?.addPreference(
                InlineActivityPreference(
                    requireContext(),
                    Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                ).apply {
                    title = resources.getString(R.string.intro_system_alert_window)
                    summary = resources.getString(R.string.intro_system_alert_window_desc)
                    icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_help_outline_24)
                }
            )
        }
    }

    private fun construct(pref: PersistentPreference): PersistentPreference {
        return 