package com.example.task.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.task.R
import com.example.task.databinding.ActivityFullPreviewImageBinding

class FullPreviewImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullPreviewImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullPreviewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews(){
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
        val imageUriString = intent.getStringExtra("imageUri")
        Log.d("TAG", "initViews1111: $imageUriString")
        val imageUri = Uri.parse(imageUriString)
        binding.fullImg.setImageURI(imageUri)
    }
}