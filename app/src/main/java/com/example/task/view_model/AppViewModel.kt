package com.example.task.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task.model.AllImages
import com.example.task.repo.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val applicationRepo: AppRepo
) :ViewModel() {

    var allImagesList: MutableLiveData<ArrayList<AllImages>> = MutableLiveData()
    fun getImages(){
        applicationRepo.getAllImagesFromStorage {
            allImagesList.value=it as ArrayList<AllImages>
        }
    }


}

/*
@HiltViewModel
class AppViewModel @Inject constructor(
    private val applicationRepo: AppRepo
) : ViewModel() {

    var allImagesList: MutableLiveData<List<AllImages>> = MutableLiveData()

    private val folderPath = "/storage/emulated/0/Screenshots/" // Example folder path

    fun getImages() {
        applicationRepo.getImagesFromFolder(folderPath) { images ->
            val allImages = listOf(AllImages(folderPath, images))
            allImagesList.postValue(allImages) // Change setValue to postValue
        }
    }
}*/
