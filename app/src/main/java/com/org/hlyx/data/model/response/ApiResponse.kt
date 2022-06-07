package com.org.hlyx.data.model.response

import com.org.utilspack.network.BaseResponse


/**
 * 描述　:服务器返回数据的基类
 * 如果你的项目中有基类，那美滋滋，可以继承BaseResponse，请求时框架可以帮你自动脱壳，自动判断是否请求成功，怎么做：
 * 1.继承 BaseResponse
 * 2.重写isSucces 方法，编写你的业务需求，根据自己的条件判断数据是否请求成功
 * 3.重写 getResponseCode、getResponseData、getResponseMsg方法，传入你的 code data msg
 */
data class ApiResponse<T>(var code: Int, var message: String, var data: T) :
    BaseResponse<T>() {

    override fun isSucces() = code == 200

    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = message

}