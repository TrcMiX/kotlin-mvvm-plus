package com.org.utilspack.network

import com.org.utilspack.R
import com.org.utilspack.base.appContext

/**
 * 作者　: TrcMiX
 * 描述　:自定义错误信息异常
 */
class AppException : Exception {

    var errorMsg: String //错误消息
    var errCode: Int = 0 //错误码
    var errorLog: String? //错误日志

    constructor(errCode: Int, error: String?, errorLog: String? = "") : super(error) {
        this.errorMsg = error ?: appContext.getString(R.string.req_error)
        this.errCode = errCode
        this.errorLog = errorLog ?: this.errorMsg
    }

    constructor(error: Error, e: Throwable?) {
        errCode = error.getKey()
        errorMsg = error.getValue()
        errorLog = e?.message
    }
}