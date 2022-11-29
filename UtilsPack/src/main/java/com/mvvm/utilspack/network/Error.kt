package com.mvvm.utilspack.network

import com.mvvm.utilspack.R
import com.mvvm.utilspack.base.appContext

/**
 * 作者　: TrcMiX
 * 描述　: 错误枚举类
 */
enum class Error(private val code: Int, private val err: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, appContext.getString(R.string.req_error)),

    /**
     * 解析错误
     */
    PARSE_ERROR(1001, appContext.getString(R.string.json_parse)),

    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, appContext.getString(R.string.internet_error)),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, appContext.getString(R.string.ssl_error)),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, appContext.getString(R.string.internet_time_out));

    fun getValue(): String {
        return err
    }

    fun getKey(): Int {
        return code
    }

}