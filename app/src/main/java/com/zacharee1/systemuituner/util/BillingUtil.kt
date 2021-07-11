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

class BillingUtil(private val dialog: DonateDialog) : CoroutineScope by MainScope() {
    private val client: BillingClient

    private val activity: Activity = dialog.context.extractActivity()

    init {
        client = BillingClient.newBuilder(dialog.context).setListener { response, purchases ->
            if (response.responseCode == OK && purchases != null) {
                for (purchase in purchases) {
                    consumeAsync(purchase.purchaseToken)
                }
            }
        }.enablePendingPurchases().build()

        val dialogBinding = LayoutDonateBinding.bind(dialog.view)

        client.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                dialog.view.post {
                    dialogBinding.paypalTitle