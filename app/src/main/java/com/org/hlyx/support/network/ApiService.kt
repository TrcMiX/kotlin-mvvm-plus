package com.org.hlyx.support.network

import com.org.hlyx.BuildConfig
import com.org.hlyx.data.model.PostRequestBody
import com.org.hlyx.data.model.ResponseBody
import com.org.hlyx.data.model.response.ApiResponse
import retrofit2.http.*

/**
 * 作者　: TrcMiX
 * 描述　: 网络API
 */
interface ApiService {

    companion object {
        const val SERVER_URL = BuildConfig.API_HOST
    }

}