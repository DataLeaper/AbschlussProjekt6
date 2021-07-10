package com.zacharee1.systemuituner.util

import android.app.Activity
import android.content.Context
import androidx.core.view.isVisible
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.BillingResponseCode.OK
import com.android.billingclient.api.QueryProductDetailsParams.Product
import com.zacharee1.systemuituner.databinding.LayoutDonateBinding
import com.zacharee1.systemuituner.dialogs.DonateDialog
import kotlinx.coroutines.*

class BillingUtil(private val dialo