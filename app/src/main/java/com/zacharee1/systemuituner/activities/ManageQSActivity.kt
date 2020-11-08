package com.zacharee1.systemuituner.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.databinding.ActivityManageQsBinding
import com.zacharee1.systemuituner.dialogs.RoundedBottomSheetDialog
import com.zacharee1.systemuituner.util.addAnimation

class ManageQSActivity : AppCompatActivity() {
    private val binding by lazy { ActivityManageQsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.addAnimation()

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       