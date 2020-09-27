package com.zacharee1.systemuituner

import android.app.ActivityManager
import android.app.ActivityThread
import android.app.Application
import android.app.ApplicationErrorReport.ParcelableCrashInfo
import android.app.IApplicationThread
import android.content.*
import android.os.*
import android.util.AndroidRuntimeException
import android.util.Log
import androidx.core.content.ContextCompat
import com.bugsnag.android.Bugsnag
import com.zacharee1.systemuituner.services.Manager
import com.zacharee1.systemui