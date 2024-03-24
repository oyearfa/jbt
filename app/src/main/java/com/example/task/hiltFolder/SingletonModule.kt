package com.example.task.hiltFolder

import android.content.Context
import com.example.task.adapter.ImagesAdapter
import com.example.task.repo.AppRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object SingletonModule {

    @Provides
    fun appRepo(@ApplicationContext context: Context): AppRepo {
        return AppRepo(context)
    }

//    @Provides
//    fun photosFolderAdapter(@ApplicationContext context: Context): PhotoFolderAdapter {
//        return PhotoFolderAdapter(context)
//    }

    @Provides
    fun imagesAdapter(@ApplicationContext context: Context): ImagesAdapter {
        return ImagesAdapter(context)
    }

}