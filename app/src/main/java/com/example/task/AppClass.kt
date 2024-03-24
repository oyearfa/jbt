package com.example.task

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.task.helper.MyForegroundService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppClass: Application(), LifecycleObserver {

    var intent: Intent? = null


    override fun onCreate() {
        super.onCreate()

        val intent = Intent(this, MyForegroundService::class.java)
        startService(intent)

        instance = this
        app_class = this@AppClass
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        applicationInstance = this@AppClass
        instance = this@AppClass

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())

        app_class = this@AppClass
    }


    companion object {
        lateinit var instance: AppClass

//        val nativeAdMain: MutableLiveData<NativeAd?> = MutableLiveData()

        var isAlready = false

        @SuppressLint("StaticFieldLeak")
        @get:Synchronized
        var app_class: AppClass? = null

        val context: Context get() = (instance as AppClass).applicationContext
        var applicationInstance: AppClass? = null

    }


}