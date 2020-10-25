package com.zacharee1.systemuituner

import android.animation.ValueAnimator
import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.zacharee1.systemuituner.activities.intro.ComposeIntroActivity
import com.zacharee1.systemuituner.databinding.ActivityMainBinding
import com.zacharee1.systemuituner.fragments.BasePrefFragment
import com.zacharee1.systemuituner.fragments.HomeFragment
import com.zacharee1.systemuituner.fragments.SearchFragment
import com.zacharee1.systemuituner.util.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private val mainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val searchFragment by lazy { supportFragmentManager.findFragmentById(R.id.search_fragment) as SearchFragment }
    private val homeFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_home_fragment) as HomeFragment }
    private val titleSwitcher by lazy { mainBinding.screenTitle }

    private val navFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment }
    private val navController: NavController
        get() = navFragment.navController

    private val searchView by lazy { mainBinding.searchBar }

    private val introLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        val (introReasons, needsResult) = run {
            val reasons = arrayListOf<ComposeIntroActivity.Companion.StartReason>()
            var needsResult = false

            if (!prefManager.didIntro) {
                if (hasWss) {
                    prefManager.didIntro = true
                }

                reasons.add(ComposeIntroActivity.Companion.StartReason.INTRO)
            }

            if (!hasWss) {
                reasons.add(ComposeIntroActivity.Companion.StartReason.WRITE_SECURE_SETTINGS)
                needsResult