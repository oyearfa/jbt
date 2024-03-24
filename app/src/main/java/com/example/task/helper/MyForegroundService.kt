package com.example.task.helper


/*
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.task.ui.MainActivity
import com.example.task.R
import com.example.task.listener.ScreenshotBroadcastReceiver

class MyForegroundService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        return START_NOT_STICKY
    }

    @SuppressLint("RemoteViewLayout")
    private fun createNotification(): Notification {
        // Create notification channel if targeting Android Oreo or higher
        val channelId = "foreground_service_channel"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification)

        // Create the intent for launching MainActivity
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create the intent for taking a screenshot
        val screenshotIntent = Intent(this, ScreenshotBroadcastReceiver::class.java)
        screenshotIntent.action = ACTION_SCREENSHOT

        // Create a PendingIntent for the broadcast
        val screenshotPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            screenshotIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create the intent for cancelling the action
        val cancelIntent = Intent(this, ScreenshotBroadcastReceiver::class.java)
        cancelIntent.action = ACTION_CANCEL
        val cancelPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            cancelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Set click actions for the buttons in the custom layout
        notificationLayout.setOnClickPendingIntent(R.id.screenshot, screenshotPendingIntent)
        notificationLayout.setOnClickPendingIntent(R.id.cancel, cancelPendingIntent)

        // Build the notification
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification_icon)
            .setContent(notificationLayout)
            .setContentIntent(pendingIntent)
            .setOngoing(true) // Keeps the notification persistent

        return builder.build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val NOTIFICATION_ID = 1001
        const val ACTION_SCREENSHOT = "com.example.app.ACTION_SCREENSHOT"
        const val ACTION_CANCEL = "com.example.app.ACTION_CANCEL"
    }
}
*/



import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.task.ui.MainActivity
import com.example.task.R
import com.example.task.listener.ScreenshotBroadcastReceiver


class MyForegroundService : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        return START_NOT_STICKY
    }

    @SuppressLint("RemoteViewLayout")
    private fun createNotification(): Notification {
        // Create notification channel if targeting Android Oreo or higher
        val channelId = "foreground_service_channel"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification)

        // Create the intent for launching MainActivity
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

 // Create the intent for taking a screenshot
        val screenshotIntent = Intent(this, ScreenshotBroadcastReceiver::class.java)
        screenshotIntent.action = ACTION_SCREENSHOT
//        val screenshotInfo = Bundle()
//        screenshotIntent.putExtra("resultCode", 1)
//        screenshotIntent.putExtra("data", screenshotInfo)
//        sendBroadcast(screenshotIntent)
        val screenshotPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            screenshotIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        // Create the intent for cancelling the action
        val cancelIntent = Intent(this, ScreenshotBroadcastReceiver::class.java)
        cancelIntent.action = ACTION_CANCEL
        val cancelPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            cancelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Set click actions for the buttons in the custom layout
        notificationLayout.setOnClickPendingIntent(R.id.screenshot, screenshotPendingIntent)
        notificationLayout.setOnClickPendingIntent(R.id.cancel, cancelPendingIntent)

//        // Set the content of the custom layout
//        notificationLayout.setTextViewText(R.id.notificationTitle, "Foreground Service")
//        notificationLayout.setTextViewText(R.id.notificationText, "Running...")

        // Build the notification
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification_icon)
            .setContent(notificationLayout)
            .setContentIntent(pendingIntent)
            .setOngoing(true) // Keeps the notification persistent

        return builder.build()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val NOTIFICATION_ID = 1001
        const val ACTION_SCREENSHOT = "com.example.app.ACTION_SCREENSHOT"
        const val ACTION_CANCEL = "com.example.app.ACTION_CANCEL"
    }
}
