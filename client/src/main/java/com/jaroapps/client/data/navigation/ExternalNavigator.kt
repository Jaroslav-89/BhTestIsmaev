package com.jar89.playlistmaker.sharing.data.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.jaroapps.client.R

internal class ExternalNavigator(private val context: Context) {
    fun openBrowser() {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.google_url))).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        browserIntent.setPackage(context.getString(R.string.browser))
        context.startActivity(browserIntent)
    }
}