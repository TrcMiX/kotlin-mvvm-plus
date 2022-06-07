package com.org.utilspack.state

import androidx.lifecycle.MutableLiveData
import com.org.utilspack.R
import com.org.utilspack.base.appContext
import com.org.utilspack.network.AppException
import com.org.utilspack.network.BaseResponse
import com.org.utilspack.network.ExceptionHandle
import retrofit2.HttpException

/**
 * 作者　: TrcMiX
 * 描述　: 自定义结果集封装类
 */
sealed class ResultState<out T> {
    companion object {
        fun <T> onAppSuccess(data: T): ResultState<T> = Success(data)
        fun <T> onAppLoading(loadingMessage: String): ResultState<T> = Loading(loadingMessage)
        fun <T> onAppError(error: AppException): ResultState<T> = Error(error)
        fun <T> onAppToLogin(error: AppException): ResultState<T> = ToLogin(error)
    }

    data class Loading(val loadingMessage: String) : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: AppException) : ResultState<Nothing>()
    data class ToLogin(val error: AppException) : ResultState<Nothing>()
}


/**
 * 处理返回值
 * @param result 请求结果
 */
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: BaseResponse<T>) {
    value = when {
        result.isSucces() -> ResultState.onAppSuccess(result.getResponseData())
        else -> ResultState.onAppError(AppException(result.getResponseCode(), result.getResponseMsg()))
    }
}

/**
 * 不处理返回值 直接返回请求结果
 * @param result 请求结果
 */
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: T) {
    value = ResultState.onAppSuccess(result)
}

/**
 * 异常转换异常处理
 */
fun <T> MutableLiveData<ResultState<T>>.paresException(e: Throwable) {
    if ((e is HttpException)&& e.code() == 401) {
        this.value = ResultState.onAppToLogin(
            AppException(
                401,
                appContext.getString(R.string.relogin)
//                Utils.get(
//                    DataBean.ApiResponse::class.java,
//                    e.response()?.errorBody()?.string()
//                )?.msg
            )
        )
        return
    }
    this.value = ResultState.onAppError(ExceptionHandle.handleException(e))
}

