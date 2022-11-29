package com.mvvm.plus.support.network

import com.blankj.utilcode.util.SPUtils
import com.mvvm.plus.support.util.SettingUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 自定义头部参数拦截器，传入heads
 */
class MyHeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val string = SPUtils.getInstance().getString("KEY_LOCALE")
        val builder = chain.request().newBuilder()
        builder.addHeader("content-language", SettingUtil.getLanguageTag(string)).build()
        return chain.proceed(builder.build())
    }

}