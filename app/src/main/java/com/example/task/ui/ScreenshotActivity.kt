package com.example.task.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task.AppClass.Companion.context
import com.example.task.extension.saveScreenshotToGallery
import com.example.task.extension.takeScreenshot
import java.io.File

class ScreenshotActivity : AppCompatActivity() {

    private var imageBitmap: Bitmap? = null // Declare the variable here


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imagePath = takeScreenshot(this)
        if (!imagePath.isNullOrEmpty()) {
            val imageFile = File(imagePath)
            imageBitmap =
                BitmapFactory.decodeFile(imageFile.absolutePath) // Assign the bitmap here
            Log.d("TAG", "onReceive: $imageBitmap")

            // Save screenshot to gallery
            imageBitmap?.let { saveScreenshotToGallery(this, it) }
        } else {
            Toast.makeText(context, "imagePath is null", Toast.LENGTH_SHORT).show()
//            finish()
        }

    }

}