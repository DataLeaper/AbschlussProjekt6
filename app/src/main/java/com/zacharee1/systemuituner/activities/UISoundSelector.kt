package com.zacharee1.systemuituner.activities

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.system.Os
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.zacharee1.systemuituner.IUISoundSelectionCallback
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.util.callSafely
import java.io.File
import java.io.IOException

class UISoundSelector : AppCompatActivity() {
    companion object {
        private const val EXTRA_KEY = "key"
        private const val EXTRA_CALLBACK = "callback"

        fun start(context: Context, key: String, callback: IUISoundSelectionCallback) {
            val activity = Intent(context, UISoundSelector::class.java)
            activity.putExtra(EXTRA_KEY, key)
            activity.