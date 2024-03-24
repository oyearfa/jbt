package com.example.task.listener

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.example.task.AppClass
import com.example.task.extension.saveImageToGallery
import com.example.task.extension.takeScreenshot
import com.example.task.helper.MyForegroundService
import java.io.File
import kotlin.system.exitProcess


class ScreenshotBroadcastReceiver : BroadcastReceiver() {
    private var imageBitmap: Bitmap? = null // Declare the variable here

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            MyForegroundService.ACTION_SCREENSHOT -> {
                // Close the notification tray
                val notificationManager =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(MyForegroundService.NOTIFICATION_ID)

                val imagePath = context.takeScreenshot(context)
                if (!imagePath.isNullOrEmpty()) {
                    val imageFile = File(imagePath)
                    imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath) // Assign the bitmap here
                    Log.d("TAG", "onReceive: $imageBitmap")

                    // Save screenshot to gallery
                    imageBitmap?.let { context.saveImageToGallery(it) }
                } else {
                    Toast.makeText(AppClass.context, "imagePath is null", Toast.LENGTH_SHORT).show()
                }

/*
                context.startActivity(
                    Intent(context, ScreenshotActivity::class.java).addFlags(
                        FLAG_ACTIVITY_NEW_TASK
                    )
                )
*/
            }

            MyForegroundService.ACTION_CANCEL -> {
                val notificationManager =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(MyForegroundService.NOTIFICATION_ID)
                val serviceIntent = Intent(context, MyForegroundService::class.java)
                context.stopService(serviceIntent)
                exitProcess(0)
            }
        }
    }

}

