package com.mvvm.plus.support.network

import com.mvvm.plus.BuildConfig

/**
 * 作者　: TrcMiX
 * 描述　: 网络API
 */
interface ApiService {

    companion object {
        const val SERVER_URL = BuildConfig.API_HOST
    }

}