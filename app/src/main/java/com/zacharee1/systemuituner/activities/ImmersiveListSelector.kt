
package com.zacharee1.systemuituner.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import com.reddit.indicatorfastscroll.FastScrollerView
import com.rey.material.widget.CheckedImageView
import com.zacharee1.systemuituner.IImmersiveSelectionCallback
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.data.LoadedAppInfo
import com.zacharee1.systemuituner.databinding.ActivityImmersiveSelectorBinding
import com.zacharee1.systemuituner.interfaces.ColorPreference
import com.zacharee1.systemuituner.util.addAnimation
import com.zacharee1.systemuituner.util.callSafely
import com.zacharee1.systemuituner.util.getColorPrimary
import com.zacharee1.systemuituner.util.getInstalledPackagesCompat
import kotlinx.coroutines.*

class ImmersiveListSelector : AppCompatActivity(), CoroutineScope by MainScope(),
    SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    companion object {
        const val EXTRA_CHECKED = "checked_packages"
        const val EXTRA_CALLBACK = "callback"

        fun start(
            context: Context,
            checked: ArrayList<String>?,
            onResultListener: IImmersiveSelectionCallback
        ) {
            val activity = Intent(context, ImmersiveListSelector::class.java)
            activity.putExtra(EXTRA_CHECKED, checked)
            activity.putExtra(
                EXTRA_CALLBACK,
                Bundle().apply { putBinder(EXTRA_CALLBACK, onResultListener.asBinder()) })

            context.startActivity(activity)
        }
    }

    val checked by lazy {
        HashSet(intent.getStringArrayListExtra(EXTRA_CHECKED) ?: ArrayList<String>())
    }
    val callback by lazy {
        val binder = intent.getBundleExtra(EXTRA_CALLBACK)?.getBinder(EXTRA_CALLBACK)
        if (binder != null) {
            IImmersiveSelectionCallback.Stub.asInterface(binder)
        } else null
    }
    private val adapter by lazy { ImmersiveAdapter(checked, this) }
    private val origItems = ArrayList<LoadedAppInfo>()
    private val binding by lazy { ActivityImmersiveSelectorBinding.inflate(layoutInflater) }

    override fun onClose(): Boolean {
        onFilter(null)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        onFilter(newText)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView?
        searchView?.setOnQueryTextListener(this)
        searchView?.setOnCloseListener(this)
        searchView?.addAnimation()

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (callback == null) {
            finish()
            return
        }

        setSupportActionBar(binding.toolbar)
        binding.toolbar.addAnimation()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_check_24)

        binding.selector.adapter = adapter
        binding.scroller.useDefaultScroller = false
        binding.scroller.itemIndicatorSelectedCallbacks += object :
            FastScrollerView.ItemIndicatorSelectedCallback {
            override fun onItemIndicatorSelected(
                indicator: FastScrollItemIndicator,
                indicatorCenterY: Int,
                itemPosition: Int
            ) {
                binding.selector.scrollToPosition(itemPosition)
            }
        }
        binding.scroller.setupWithRecyclerView(
            binding.selector,
            { position ->