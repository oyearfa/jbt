package com.example.task.ui


import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.task.adapter.ImagesAdapter
import com.example.task.databinding.ActivityMainBinding
import com.example.task.extension.beGone
import com.example.task.extension.beVisible
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
        Log.d("TAG", "initViews: ${viewModel.getImages()}")
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