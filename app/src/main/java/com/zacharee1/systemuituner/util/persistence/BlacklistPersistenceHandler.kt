package com.zacharee1.systemuituner.util.persistence

import android.content.Context
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.prefManager
import com.zacharee1.systemuituner.util.writeSetting

class BlacklistPersistenceHandler(context: Context) : BasePersistenceHandler<HashSet<String>>(context) {
    override val settingsKey: String = "icon_blacklist"
    override val settingsType: SettingsType = SettingsType.SECURE

    override fun getPreferenceValue(): HashSet<String> {
        return context.prefManager.blacklistedItems
    }

    override fun getPreferenceValueAsString(): String {
        return getPreferenceValue().joinToString(",")
    }

    override fun savePreferenceValue(value: String?) {
        if (value == null) {
            context.prefManager.blacklistedItems = HashSet()
        } else {
            context.prefManager.blackl