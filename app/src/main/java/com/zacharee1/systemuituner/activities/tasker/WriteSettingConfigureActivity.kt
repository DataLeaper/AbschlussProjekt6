package com.zacharee1.systemuituner.activities.tasker

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.zacharee1.systemuituner.data.tasker.TaskerWriteSettingData
import com.zacharee1.systemuituner.databinding.TaskerWriteSettingBinding
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.util.tasker.helpers.WriteSettingHelper

class WriteSettingConfigureActivity : AppCompatActivity(), TaskerPluginConfig<TaskerWriteSettingData> {
    override val context: Context
        get() = this
    override val inputForTasker: TaskerInput<TaskerWriteSettingData>
        get() = TaskerInput(TaskerWriteSettingData(type, key, value))

    private var type: String = SettingsType.UNDEFINED.toString()
    p