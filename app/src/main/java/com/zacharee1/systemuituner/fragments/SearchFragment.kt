package com.zacharee1.systemuituner.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.forEach
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.SearchIndex
import com.zacharee1.systemuituner.util.hasPreference
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SearchFragment : BasePrefFragment(), SearchView.OnQueryTextListener {
    var onItemClickListener: ((action: Int, key: String?) -> Unit)? = null

    private val searchIndex by lazy { SearchIndex.getInstance(requireContext()) }

    override val limitSummary: Boolean = false

    private val prefsCategory: PreferenceCategory?
        get() = preferenceScreen.findPreference("prefs_group")

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs_search, rootKey)
    }

    fun onShow() {
        launch {
            searchIndex.load().await()

            prefsCategory?.removeAll()
            searchIndex.filter(null) {
                it.forEach { pref ->
                    prefsCategory?.addPreference(
                        SearchIndex.ActionedPreference.copy(requireContext(), pref)
                    )
                }
            }
            prefsCategory?.isOrderingAsAdded = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bun