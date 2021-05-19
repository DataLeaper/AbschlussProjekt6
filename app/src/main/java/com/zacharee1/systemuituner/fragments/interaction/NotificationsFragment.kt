package com.zacharee1.systemuituner.fragments.interaction

import android.os.Bundle
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.fragments.BasePrefFragment
import com.zacharee1.systemuituner.util.updateTitle

class NotificationsFragment : BasePrefFragment() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs_notification