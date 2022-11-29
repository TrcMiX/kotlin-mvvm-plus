package com.mvvm.utilspack.util

import android.app.Activity
import com.google.gson.Gson

object Utils {

    fun <T> get(clazz: Class<T>, s: String?): T? {
        return Gson().fromJson(s, clazz)
    }

    fun reViewActivity(activity: Activity) {
        val intent = activity.intent
        activity.overridePendingTransition(0, 0)
        activity.finish()
        activity.overridePendingTransition(0, 0)
        activity.startActivity(intent)
    }
}