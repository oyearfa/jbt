package com.example.task

import android.content.Context
import com.example.task.extension.getSharedPrefs

open class BaseConfig(val context: Context) {
    protected val prefs = context.getSharedPrefs()



    companion object {
        fun newInstance(context: Context) = BaseConfig(context)
    }


    var delete: String?
        get() = prefs.getString("delete", "")
        set(deleted) = prefs.edit().putString("delete", deleted).apply()


}

