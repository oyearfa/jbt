package com.example.task.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.task.R
import com.example.task.databinding.ItemViewImagesBinding
import com.example.task.ui.FullPreviewImageActivity
import java.io.File

class ImagesAdapter(val context: Context) : RecyclerView.Adapter<ImagesAdapter.ImageItem>() {
    private val TAG = "ImagesFolderAdapter"
    var onImageClick: ((Uri, Int) -> Unit?)? = null
    var onImageDelete: ((Int) -> Unit)? = null // Listener for delete action

    fun onImageClicked(downloadClick: ((Uri, Int) -> Unit)) {
        onImageClick = downloadClick
    }

    private var activity: Activity? = null
    private var images: ArrayList<Uri> = arrayListOf()


    fun updateData(images: ArrayList<Uri>, cntxt: Activity) {
        activity = cntxt
        this.images = images
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItem {

        val inflater = ItemViewImagesBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImageItem(
            inflater
        )
    }

    override fun onBindViewHolder(holder: ImageItem, position: Int) {
        holder.bind(images[position], position)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageItem(val binding: ItemViewImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Uri, position: Int) {
            val requestOptions: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_file_place_holder)
                .error(R.drawable.ic_file_place_holder)
            Glide.with(context)
                .load(image.path)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivWebIcon)

            /*  binding.root.setOnClickListener {
                  onImageClick?.invoke(image, position)
              }*/

            binding.btnMenu.setOnClickListener {
                showPopupMenu(it, image, position)
            }

        }

        private fun showPopupMenu(view: View, image: Uri, position: Int) {
            val popupMenu = PopupMenu(context, view)
            popupMenu.menuInflater.inflate(R.menu.image_popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_delete -> {
                        deleteImage(position)
                        true
                    }

                    R.id.menu_share -> {
                        shareImage(context,image)
                        true
                    }

                    R.id.menu_open -> {
                        openImage(image)
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }

        private fun deleteImage(position: Int) {
            // Remove the image from the dataset
            images.removeAt(position)

            // Notify adapter about the item removal
            notifyItemRemoved(position)

            // Optionally, you can also notify about the range changed
            // notifyItemRangeChanged(position, itemCount)

            // Notify the listener about the delete action
            onImageDelete?.invoke(position)
        }


        private fun shareImage(context: Context, imageUri: Uri) {
            // Define the authority of the FileProvider
            val authority = "${context.packageName}.provider"

            // Get the URI for sharing
            val shareUri = FileProvider.getUriForFile(context, authority, File(imageUri.path))

            // Create a share intent
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, shareUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            // Start activity for sharing
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
        }


//        private fun shareImage(context: Context, imageUri: Uri) {
//            // Get content resolver from the context
//            val contentResolver = context.contentResolver
//
//            // Insert the image into the media store
//            val pathOfBmp = MediaStore.Images.Media.insertImage(
//                contentResolver, imageUri.toString(), "title", null
//            )
//
//            // Parse the URI
//            val uri = Uri.parse(pathOfBmp)
//
//            // Create a share intent
//            val shareIntent = Intent(Intent.ACTION_SEND)
//            shareIntent.type = "image/*"
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Star App")
//            shareIntent.putExtra(Intent.EXTRA_TEXT, "")
//            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
//
//            // Start activity for sharing
//            context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
//        }

//        private fun openImage(imageUri: Uri) {
//            val intent = Intent(context, FullPreviewImageActivity::class.java).apply {
//                putExtra("imageUri", imageUri.toString())
//            }
//            context.startActivity(intent)
//        }

        private fun openImage(imageUri: Uri) {
            val intent = Intent(context, FullPreviewImageActivity::class.java).apply {
                putExtra("imageUri", imageUri.toString())
            }
            if (context is Activity) {
                context.startActivity(intent)
            } else {
                Log.e(TAG, "Context is not an Activity, cannot start FullPreviewImageActivity")
            }
        }

    }
}
