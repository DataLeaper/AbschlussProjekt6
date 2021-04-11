package com.zacharee1.systemuituner.fragments

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.*
import androidx.preference.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import at.blogc.android.views.ExpandableTextView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.anim.PrefAnimator
import com.zacharee1.systemuituner.data.PreferenceHolder
import com.zacharee1.systemuituner.dialogs.*
import com.zacharee1.systemuituner.prefs.*
import com.zacharee1.systemuituner.prefs.demo.DemoListPreference
import com.zacharee1.systemuituner.prefs.demo.DemoSeekBarPreference
import com.zacharee1.systemuituner.prefs.demo.DemoSwitchPreference
import com.zacharee1.systemuituner.prefs.secure.SecureEditTextPreference
import com.zacharee1.systemuituner.prefs.secure.SecureListPreference
import com.zacharee1.systemuituner.prefs.secure.SecureSeekBarPreference
import com.zacharee1.systemuituner.prefs.secure.SecureSwitchPreference
import com.zacharee1.systemuituner.prefs.secure.specific.*
import com.zacharee1.systemuituner.util.*
i