package com.zacharee1.systemuituner.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.QSTileInfo
import com.zacharee1.systemuituner.data.SettingsType
import com.zacharee1.systemuituner.databinding.ActivityQsEditorBinding
import com.zacharee1.systemuituner.databinding.QsTileBinding
import com.zacharee1.systemuituner.dialogs.AddQSTileDialog
import com.zacharee1.systemuituner.dialogs.RoundedBottomSheetDialog
import com.zacharee1.systemuituner.util.*
import com.zacharee1.systemuituner.views.GridAutofitLayoutManager
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.max

class QSEditorActivity : CoroutineActivity() {
    private val adapter by lazy { QSEditorAdapter(this) }
    private val binding by lazy { ActivityQsEditorBinding.inflate(layoutInflater) }

    private val touchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: