package com.org.utilspack.data

class DataBean{
    data class ApiResponse<T>(var code: Int, var msg: String, var data: T)
}