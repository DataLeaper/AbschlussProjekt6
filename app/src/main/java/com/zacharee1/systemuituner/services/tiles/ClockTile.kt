
package com.zacharee1.systemuituner.services.tiles

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.AlarmClock
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.text.format.DateFormat
import com.zacharee1.systemuituner.util.safeUpdateTile
import java.text.SimpleDateFormat
import java.util.*

@TargetApi(Build.VERSION_CODES.N)
class ClockTile : TileService() {