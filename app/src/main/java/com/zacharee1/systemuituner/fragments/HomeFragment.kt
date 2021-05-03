
package com.zacharee1.systemuituner.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.preference.*
import com.zacharee1.systemuituner.App
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.prefs.NavHeaderPreference
import com.zacharee1.systemuituner.prefs.nav.NavigationPreference
import com.zacharee1.systemuituner.util.isTouchWiz

class HomeFragment : BasePrefFragment(), NavController.OnDestinationChangedListener {
    var onSearchClickListener: (() -> Unit)? = null

    override val supportsGrid = false

    private var selectedId: String? = null

    private val navPrefs = hashMapOf<Int, NavigationPreference>()

    override val paddingDp = arrayOf(0f, 0f, 0f, 0f)
    override val preferencePadding: ((Preference) -> Array<Float>) = {
        if (it is NavHeaderPreference) {
            arrayOf(0f, 0f, 0f, 0f)
        } else {
            arrayOf(16f, 0f, 16f, 0f)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs_home, rootKey)

        findPreference<NavHeaderPreference>("header")
            ?.onSearchClickListener = { onSearchClickListener?.invoke() }
    }

    override fun onResume() {
        super.onResume()

        App.updateServiceState(requireContext())

        preferenceScreen.forEach {
            fun run(pref: Preference) {
                if (pref is NavigationPreference) {
                    navPrefs[pref.destId] = pref
                }
            }

            if (it is PreferenceGroup) {
                it.forEach { pref ->
                    run(pref)
                }
            } else {
                run(it)
            }
        }

        requireActivity().findNavController(R.id.nav_host_fragment).apply {
            removeOnDestinationChangedListener(this@HomeFragment)
            addOnDestinationChangedListener(this@HomeFragment)
        }

        findPreference<Preference>("reset_options")?.apply {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                isVisible = false
            }
        }

        findPreference<Preference>("oneui_tuner_option")?.apply {
            if (!requireContext().isTouchWiz || Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                isVisible = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            requireActivity().findNavController(R.id.nav_host_fragment).removeOnDestinationChangedListener(this)
        } catch (_: Exception) {}
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {