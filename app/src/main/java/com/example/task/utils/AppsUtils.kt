package com.example.task.utils

import android.content.Context
import android.content.Intent


object AppsUtils {
    fun shareApp(context: Context) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Hey check out my app at: https://play.google.com/store/apps/details?id=" + context.packageName
        )
        sendIntent.type = "text/plain"
        context.startActivity(sendIntent)
    }
}
