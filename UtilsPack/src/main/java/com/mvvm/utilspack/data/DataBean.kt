package com.mvvm.utilspack.data

class DataBean{
    data class ApiResponse<T>(var code: Int, var msg: String, var data: T)
}