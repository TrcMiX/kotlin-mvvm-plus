package com.mvvm.plus.support.util

import android.text.TextUtils
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

object CacheUtil {
    val LOGIN_CACHE="islogin"
    /**
     * 是否是第一次登陆
     */
    fun isFirst(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeBool("first", true)
    }


    fun <T> save(objects: T, tag: String) {
        val kv = MMKV.mmkvWithID("app")
        if (objects == null) {
            kv.encode(tag, "")
        } else {
            kv.encode(tag, Gson().toJson(objects))
        }
    }

    fun <T> get(clazz: Class<T>, tag: String): T? {
        val kv = MMKV.mmkvWithID("app")
        val json = kv.decodeString(tag)
        return if (TextUtils.isEmpty(json)) {
            null
        } else {
            Gson().fromJson(json, clazz)
        }
    }
}