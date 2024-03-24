package com.example.task.repo

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.task.model.AllImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AppRepo(val context: Context) {

    fun getAllImagesFromStorage(callback: (List<AllImages>) -> Unit) {
        val allImages = mutableListOf<AllImages>()
        val images = mutableListOf<Uri>()
        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver: ContentResolver? = context.contentResolver
            val cursor: Cursor? = contentResolver?.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.OWNER_PACKAGE_NAME,
                    MediaStore.Images.Media._ID
                ),
                null,
                null,
                MediaStore.Images.Media.DATE_MODIFIED + " DESC"
            )

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val data =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    val imageData = Uri.parse(data)
                    images.add(imageData)
                }
                cursor.close()

            } else {
                Log.e(ContentValues.TAG, "Cursor is null or empty.")
            }

            // Group images by folder
            val folderMap = mutableMapOf<String, MutableList<Uri>>()
            images.forEach { imageData ->
                val parent = imageData.path?.let { File(it).parent }
                val folderName = parent?.split("/")?.last() ?: "Unknown"
                if (folderMap.containsKey(folderName)) {
                    folderMap[folderName]?.add(imageData)
                } else {
                    folderMap[folderName] = mutableListOf(imageData)
                }
            }

            // Create AllImages objects from the grouped data
            folderMap.forEach { (folderName, imagesInFolder) ->
                if (folderName=="Screenshots"){
                    allImages.add(AllImages(folderName, imagesInFolder))
                }
            }

            Log.d(ContentValues.TAG, "getAllImagesFromStorage: all images size ${images.size}")
            Log.d(ContentValues.TAG, "getAllImagesFromStorage: ${allImages.size}")

        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                callback.invoke(allImages)
            }
        }
    }



    fun getImagesFromFolder(folderPath: String, callback: (List<Uri>) -> Unit) {
        val images = mutableListOf<Uri>()
        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver: ContentResolver? = context.contentResolver
            val cursor: Cursor? = contentResolver?.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media.DATA
                ),
                "${MediaStore.Images.Media.DATA} like ?",
                arrayOf("$folderPath%"),
                MediaStore.Images.Media.DATE_MODIFIED + " DESC"
            )

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val data =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    val imageData = Uri.parse(data)
                    images.add(imageData)
                }
                cursor.close()
            } else {
                Log.e(ContentValues.TAG, "Cursor is null or empty.")
            }
            callback.invoke(images)
        }
    }


   /* fun getAllImagesFromFolder(folderName: String, callback: (List<AllImages>) -> Unit) {
        val allImages = mutableListOf<AllImages>()
        val images = mutableListOf<Uri>()
        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver: ContentResolver? = context.contentResolver
            val cursor: Cursor? = contentResolver?.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.OWNER_PACKAGE_NAME,
                    MediaStore.Images.Media._ID
                ),
                "${MediaStore.Images.Media.DATA} like ?",
                arrayOf("%$folderName%"),
                MediaStore.Images.Media.DATE_MODIFIED + " DESC"
            )

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val data =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                    val imageData = Uri.parse(data)
                    images.add(imageData)
                }
                cursor.close()
            } else {
                Log.e(ContentValues.TAG, "Cursor is null or empty.")
            }

            // Group images by folder
            val folderMap = mutableMapOf<String, MutableList<Uri>>()
            images.forEach { imageData ->
                val parent = imageData.path?.let { File(it).parent }
                val folderName = parent?.split("/")?.last() ?: "Unknown"
                if (folderMap.containsKey(folderName)) {
                    folderMap[folderName]?.add(imageData)
                } else {
                    folderMap[folderName] = mutableListOf(imageData)
                }
            }

            // Create AllImages objects from the grouped data
            folderMap.forEach { (folderName, imagesInFolder) ->
                if (folderName == "Screenshots") {
                    allImages.add(AllImages(folderName, imagesInFolder))
                }
            }

            Log.d(ContentValues.TAG, "getAllImagesFromStorage: all images size ${images.size}")
            Log.d(ContentValues.TAG, "getAllImagesFromStorage: ${allImages.size}")

        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                callback.invoke(allImages)
            }
        }
    }*/

}
