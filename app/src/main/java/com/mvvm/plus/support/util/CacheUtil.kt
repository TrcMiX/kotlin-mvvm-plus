package com.mvvm.plus.support.util

import android.text.TextUtils
import com.google.gson.Gson
import com.mvvm.plus.data.model.DataBean
import com.tencent.mmkv.MMKV

object CacheUtil {
    val LOGIN_CACHE = "islogin"

    /**
     * 获取保存的账户信息
     */
    fun getUser(): DataBean.UserInfo? {
        val kv = MMKV.mmkvWithID("app")
        val userStr = kv.decodeString("user")
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            Gson().fromJson(userStr, DataBean.UserInfo::class.java)
        }
    }

    /**
     * 设置账户信息
     */
    fun setUser(userResponse: DataBean.UserInfo?) {
        val kv = MMKV.mmkvWithID("app")
        if (userResponse == null) {
            kv.encode("user", "")
            setIsLogin(false)
        } else {
            kv.encode("user", Gson().toJson(userResponse))
            setIsLogin(true)
        }

    }

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

    /**
     * 是否已经登录
     */
    fun isLogin(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeBool("login", false)
    }

    /**
     * 设置是否已经登录
     */
    fun setIsLogin(isLogin: Boolean) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("login", isLogin)
    }
}