package com.example.task.ui

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.task.AppClass.Companion.context
import com.example.task.R
import saveImageToGallery
import takeScreenshot
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class ScreenshotActivity : AppCompatActivity() {

    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screensheet) // Assuming you have a layout

        requestPermissions()

        val imagePath = takeScreenshot()
        if (!imagePath.isNullOrEmpty()) {
            val imageFile = File(imagePath)
            imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            Log.d("TAG", "onReceive: $imageBitmap")

            // Save screenshot to gallery
            imageBitmap?.let { saveImageToGallery(this, it) }
        } else {
            Toast.makeText(this, "imagePath is null", Toast.LENGTH_SHORT).show()
        }
    }


    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
        }
    }


    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }
}
