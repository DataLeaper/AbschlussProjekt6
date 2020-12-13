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
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.move(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
            return true
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)

            viewHolder as QSEditorAdapter.QSVH?

            viewHolder?.apply {
                showRemove = !showRemove
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        actionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        fun updateLayout() {
            val fullWidth = binding.root.width
            val sidePadding = max(0f, (fullWidth - dpAsPx(800)) / 2f).toInt()

            binding.qsList.setPaddingRelative(
                sidePadding, 0,
                sidePadding, 0
            )

            binding.qsList.layoutManager = GridAutofitLayoutManager(this, dpAsPx(130))
        }

        updateLayout()

        binding.root.addOnLayoutChangeListener { _, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
                updateLayout()
            }
        }

        binding.qsList.adapter = adapter

        ItemTouchHelper(touchHelperCallback).attachToRecyclerView(binding.qsList)

        adapter.populateTiles()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_qs_editor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                AddQSTileDialog(this, adapter)
                    .show()
                true
            }
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        adapter.saveTiles()
    }

    inner class QSEditorAdapter(private val context: Context) : RecyclerView.Adapter<QSEditorAdapter.QSVH>() {
        @SuppressLint("DiscouragedApi")
        private val defaultTiles = ArrayList<String>().apply {
            try {
                val remRes = context.packageManager.getResourcesForApplication("com.android.systemui")
                val id = remRes.getIdentifier("quick_settings_tiles_default", "string", "com.android.systemui")
                val amazonId = try {
                    remRes.getIdentifier("amazon_quick_settings_tiles_default", "string", "com.android.systemui")
                } catch (e: Exception) {
                    0
                }
                val samsungId = try {
                    remRes.getIdentifier("sec_quick_settings_tiles_default", "string", "com.android.systemui")
                } catch (e: Exception) {
                    0
                }

                val items = when {
                    amazonId != 0 -> {
                        //Fire tablets have a lot of different default lists, so we're just
                        //going to add them manually here.
                        "wifi,bt,airplane,moonlight,privacy,home,dnd,smarthome,camera,lowpower,rotation,exitkft"
                    }
                    samsungId != 0 -> {
                        val result = remRes.getString(samsungId)
                        val tiles = result.split(",").toMutableList()

                        remRes.getString(remRes.getIdentifier("quick_settings_custom_tile_component_names", "string", "com.android.systemui"))
                            .split(",")
                            .forEach { item ->
                                val (key, _) = item.split(":")

                                t