package com.example.task.extension

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.task.BaseConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

const val PREFS_KEY = "Prefs"

fun Context.getSharedPrefs(): SharedPreferences =
    getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

val Context.baseConfig: BaseConfig get() = BaseConfig.newInstance(this)




fun Context.takeScreenshot(context: Context): String? {

    val rootView = (context as? Activity)?.window?.decorView?.rootView


    Log.d("TAG", "takeScreenshot1: $rootView")

    val bitmap = rootView?.let {
        Bitmap.createBitmap(
            rootView.width,
            it.height,
            Bitmap.Config.ARGB_8888
        )
    }
    val canvas = bitmap?.let { Canvas(it) }
    rootView?.draw(canvas)

    val fileName = "JanBark${System.currentTimeMillis()}.png"
    Log.d("TAG", "takeScreenshot2: $fileName")

    val filePath = "${context.getExternalFilesDir(Environment.DIRECTORY_DCIM)?.absolutePath}/$fileName/"


//    val filePath = "${Environment.getExternalStoragePublicDirectory("Phone/DCIM/JanBark")?.absolutePath}/$fileName"


    //test
    Log.d("TAG", "takeScreenshot3: $filePath")

    return try {
        val fileOutputStream = FileOutputStream(filePath)
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()
        filePath // Return the filePath
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}




 fun Context.saveScreenshotToGallery(context: Context, bitmap: Bitmap) {
    // Get the directory to save screenshots
    val folder = File(Environment.getExternalStorageDirectory().toString() + "/Screenshots")
    if (!folder.exists()) {
        folder.mkdirs()
    }

    // Save the bitmap to a file
    val file = File(folder, "janbark_screenshot_${System.currentTimeMillis()}.png")
    var outputStream: OutputStream? = null
    try {
        outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

        // Add the image to the gallery
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, file.name)
            put(MediaStore.Images.Media.DESCRIPTION, "Screenshot")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.DATA, file.absolutePath)
        }
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        Log.d("TAG", "Screenshot saved: ${file.absolutePath}")
//            finish()
    } catch (e: Exception) {
        Log.e("TAG", "Error saving screenshot", e)
    } finally {
        outputStream?.close()
    }
}




/*fun Context.takeScreenshot(): String? {
    // Ensure that the context is an Activity context to access its window and decorView
    if (this !is Activity) {
        Log.e("TAG", "Cannot take screenshot: Context is not an Activity")
        return null
    }

    // Get the root view of the activity's window
    val rootView = window.decorView.rootView

    // Create a bitmap of the rootView
    val bitmap = Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    rootView.draw(canvas)

    // Generate file name for the screenshot
    val fileName = "JanBark_${System.currentTimeMillis()}.png"

    // Determine the directory for storing the screenshot
    val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
    val filePath = File(directory, fileName)

    try {
        // Create a FileOutputStream for the file
        val fileOutputStream = FileOutputStream(filePath)

        // Compress and write the bitmap to the file
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)

        // Close the FileOutputStream
        fileOutputStream.close()

        // Return the absolute path of the saved screenshot
        return filePath.absolutePath
    } catch (e: IOException) {
        e.printStackTrace()
        Log.e("TAG", "Failed to save screenshot: ${e.message}")
        return null
    }
}*/


 fun Context.saveImageToGallery(bitmap: Bitmap) {
    val filename = "MyImage_${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }
    }

    val resolver = contentResolver
    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    imageUri?.let { uri ->
        val outputStream: OutputStream? = resolver.openOutputStream(uri)
        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            it.flush()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.clear()
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)
        }

    } ?: run {
        Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
    }
}
