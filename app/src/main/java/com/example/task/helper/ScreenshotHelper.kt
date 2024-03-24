package com.example.task.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer

class ScreenshotHelper(private val context: Context, private val mediaProjectionManager: MediaProjectionManager) {

    private var mediaProjection: MediaProjection? = null
    private var virtualDisplay: VirtualDisplay? = null

    @SuppressLint("WrongConstant")
    fun takeScreenshot(resultCode: Int, data: Intent?) {
        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data!!)
        if (mediaProjection != null) {
            val displayMetrics = DisplayMetrics()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels
            val density = displayMetrics.densityDpi

            val imageReader = ImageReader.newInstance(width, height, 0x1, 2)
            virtualDisplay = mediaProjection?.createVirtualDisplay(
                "Screenshot",
                width,
                height,
                density,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.surface,
                null,
                null
            )

            imageReader.setOnImageAvailableListener({ reader ->
                var image: Image? = null
                try {
                    image = reader.acquireLatestImage()
                    val buffer: ByteBuffer = image.planes[0].buffer
                    val pixelStride: Int = image.planes[0].pixelStride
                    val rowStride: Int = image.planes[0].rowStride
                    val rowPadding: Int = rowStride - pixelStride * width

                    val bitmap = Bitmap.createBitmap(
                        width + rowPadding / pixelStride,
                        height,
                        Bitmap.Config.ARGB_8888
                    )
                    Log.d("TAG", "takeScreenshot: $bitmap")
                    bitmap.copyPixelsFromBuffer(buffer)
                    saveBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    image?.close()
                    mediaProjection?.stop()
                    virtualDisplay?.release()
                }
            }, null)
        }
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/Screenshots"
        Log.d("TAG", "saveBitmap: $dirPath")
        val directory = File(dirPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val filePath = "$dirPath/Screenshot_${System.currentTimeMillis()}.png"
        try {
            val fileOutputStream = FileOutputStream(filePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
