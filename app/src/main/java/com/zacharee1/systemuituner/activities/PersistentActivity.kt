package com.zacharee1.systemuituner.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.databinding.ActivityPersistentBinding
import com.zacharee1.systemuituner.dialogs.RoundedBottomSheetDialog
import com.zacharee1.systemuituner.fragments.PersistentFragment
import com.zacharee1.systemuituner.util.addAnimation

class PersistentActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPersistentBinding.inflate(layoutInflater) }
    private val persistentFragment by lazy { supportFragmentManager.findFragmentById(R.id.persistent_fragment) as PersistentFragment }
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent