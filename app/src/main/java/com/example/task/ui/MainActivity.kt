package com.example.task.ui


import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task.adapter.ImagesAdapter
import com.example.task.databinding.ActivityMainBinding
import com.example.task.extension.beGone
import com.example.task.extension.beVisible
import com.example.task.model.Pojo
import com.example.task.view_model.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

/*class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AppViewModel by viewModels()

    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        observeViewModel()
    }

    private fun initViews() {
        binding.rvImages.layoutManager = GridLayoutManager(this, 3)
        binding.rvImages.adapter = imagesAdapter
    }

//    private fun observeViewModel() {
//        viewModel.allImagesList.observe(this) { allImagesList ->
//            if (allImagesList.isNotEmpty()) {
//                binding.clEmpty.beGone()
//                imagesAdapter.updateData(allImagesList[0].data as ArrayList<Uri>, this)
//            } else {
//                binding.clEmpty.beVisible()
//            }
//        }
//    }

    private fun observeViewModel() {
        viewModel.allImagesList.observe(this) { allImagesList ->
            if (allImagesList.isNotEmpty()) {
                binding.clEmpty.beGone()
                imagesAdapter.updateData(allImagesList.flatMap { it.data } as ArrayList<Uri>, this)
            } else {
                binding.clEmpty.beVisible()
            }
        }
    }
}*/




class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: AppViewModel by viewModels()

//    private var folderPosition="/storage/emulated/0/Android/data/com.example.task/files/Pictures/"

    private var folderPosition = 0

    @Inject
    lateinit var imagesAdapter: ImagesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.rvImages.layoutManager = GridLayoutManager(this, 3)
        binding.rvImages.adapter = imagesAdapter
        viewModel.getImages()
        viewModel.allImagesList.observe(this) {
            if (it.isNotEmpty()) {
                binding.clEmpty.beGone()
                Log.d("test", "initViews: ${it.size}")
//                photosFolderAdapter.updateData(it, this)
                imagesAdapter.updateData(it[folderPosition].data as ArrayList<Uri>, this)
            } else {
                binding.clEmpty.beVisible()
            }
        }

    }
}

//    private fun showImageMenu(image: Uri, position: Int) {
//        val popupMenu = PopupMenu(this, binding.rvImages)
//        popupMenu.menuInflater.inflate(R.menu.image_popup_menu, popupMenu.menu)
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.menu_delete -> {
//                    imagesAdapter.onImageDelete = { position ->
//                        deleteImage(position)
//                    }
//                    true
//                }
//                R.id.menu_share -> {
//                    imagesAdapter.onImageShare = { image ->
//                        shareImage(image)
//                    }
//                    true
//                }
//                R.id.menu_open -> {
//                    imagesAdapter.onImageOpen = { image ->
//                        openImage(image)
//                    }
//                    true
//                }
//                else -> false
//            }
//        }
//        popupMenu.show()
//    }
//
//    private fun deleteImage(position: Int) {
//        val images = viewModel.allImagesList.value ?: return
//        val imageToDelete = images[folderPosition].data[position]
//
//        // Perform delete operation (e.g., remove from list or delete from storage)
//        // For example:
//        // images[folderPosition].data.removeAt(position)
//        // imagesAdapter.notifyItemRemoved(position)
//
//        // You might want to update your ViewModel or perform any necessary database operation
//        // viewModel.deleteImage(imageToDelete)
//
//        // Notify adapter of item removal
//        imagesAdapter.notifyItemRemoved(position)
//    }
//    private fun shareImage(imageUri: Uri) {
//        val shareIntent = Intent().apply {
//            action = Intent.ACTION_SEND
//            putExtra(Intent.EXTRA_STREAM, imageUri)
//            type = "image/*"
//        }
//        startActivity(Intent.createChooser(shareIntent, "Share image"))
//    }
//
//    private fun openImage(imageUri: Uri) {
//        val intent = Intent(this, FullPreviewImageActivity::class.java).apply {
//            putExtra("imageUri", imageUri.toString())
//        }
//        startActivity(intent)
//    }


//}