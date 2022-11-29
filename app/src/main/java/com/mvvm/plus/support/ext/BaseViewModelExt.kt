package com.mvvm.plus.support.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.StringUtils.getString
import com.mvvm.plus.App
import com.mvvm.plus.support.util.CacheUtil
import com.mvvm.utilspack.R
import com.mvvm.utilspack.base.activity.BaseVmActivity
import com.mvvm.utilspack.base.activity.BaseVmDbActivity
import com.mvvm.utilspack.base.fragment.BaseVmFragment
import com.mvvm.utilspack.base.viewmodel.BaseViewModel
import com.mvvm.utilspack.ext.util.loge
import com.mvvm.utilspack.network.AppException
import com.mvvm.utilspack.network.BaseResponse
import com.mvvm.utilspack.network.ExceptionHandle
import com.mvvm.utilspack.state.ResultState
import com.mvvm.utilspack.state.paresException
import com.mvvm.utilspack.state.paresResult
import kotlinx.coroutines.*
import retrofit2.HttpException

/**
 * 作者　: TrcMiX
 * 描述　:BaseViewModel请求协程封装
 */

/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param resultState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 *
 */
fun <T> BaseVmDbActivity<*, *>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T?) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    showTl: Boolean = true
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading(resultState.loadingMessage)
            onLoading?.run { this }
        }
        is ResultState.Success -> {
            dismissLoading()
//            if (resultState.data != null)
            onSuccess(resultState.data)
//            else
//                onError?.run { this(AppException(4004, "")) }
        }
        is ResultState.Error -> {
            dismissLoading()
            if (showTl)
                resultState.error.errorMsg.tl()
            onError?.run {
                this(resultState.error)
            }
        }
        is ResultState.ToLogin -> {
            if (CacheUtil.get(Boolean::class.java, CacheUtil.LOGIN_CACHE) == true) {
                CacheUtil.save(false, CacheUtil.LOGIN_CACHE)
                dismissLoading()
                if (showTl)
                    resultState.error.errorMsg.tl()
                cleanStartLogin()
                onError?.run { this(resultState.error) }
            }
        }
    }
}

/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param resultState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 *
 */
fun <T> BaseVmActivity<*>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T?) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    showTl: Boolean = true
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading(resultState.loadingMessage)
            onLoading?.run { this }
        }
        is ResultState.Success -> {
            dismissLoading()
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            dismissLoading()
            if (showTl)
                resultState.error.errorMsg.tl()
            onError?.run { this(resultState.error) }
        }
        is ResultState.ToLogin -> {
            if (CacheUtil.get(Boolean::class.java, CacheUtil.LOGIN_CACHE) == true) {
                CacheUtil.save(false, CacheUtil.LOGIN_CACHE)
                dismissLoading()
                if (showTl)
                    resultState.error.errorMsg.tl()
                cleanStartLogin()
                onError?.run { this(resultState.error) }
            }
        }
    }
}

/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param resultState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 *
 */
fun <T> BaseVmFragment<*>.parseState(
    resultState: ResultState<T>,
    onSuccess: (T?) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    showTl: Boolean = true
) {
    when (resultState) {
        is ResultState.Loading -> {
            showLoading(resultState.loadingMessage)
            onLoading?.invoke()
        }
        is ResultState.Success -> {
            dismissLoading()
            onSuccess(resultState.data)
        }
        is ResultState.Error -> {
            dismissLoading()
            if (showTl)
                resultState.error.errorMsg.tl()
            onError?.run { this(resultState.error) }
        }
        is ResultState.ToLogin -> {
            if (CacheUtil.get(Boolean::class.java, CacheUtil.LOGIN_CACHE) == true) {
                CacheUtil.save(false, CacheUtil.LOGIN_CACHE)
                dismissLoading()
                if (showTl)
                    resultState.error.errorMsg.tl()
                context!!.cleanStartLogin()
                onError?.run { this(resultState.error) }
            }
        }
    }
}

/**
 * net request 不校验请求结果数据是否是成功
 * @param block 请求体方法
 * @param resultState 请求回调的ResultState数据
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    resultState: MutableLiveData<ResultState<T>>,
    isShowDialog: Boolean = false,
    loadingMessage: String = getString(R.string.reqing)
): Job {
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) resultState.value = ResultState.onAppLoading(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            resultState.paresResult(it)
        }.onFailure {
            it.message?.loge("JetPackLog")
            resultState.paresException(it)
        }
    }
}

/**
 * net request 不校验请求结果数据是否是成功
 * @param block 请求体方法
 * @param resultState 请求回调的ResultState数据
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.requestNoCheck(
    block: suspend () -> T,
    resultState: MutableLiveData<ResultState<T>>,
    isShowDialog: Boolean = false,
    loadingMessage: String =  getString(R.string.reqing)
): Job {
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) resultState.value = ResultState.onAppLoading(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            resultState.paresResult(it)
        }.onFailure {
            it.message?.loge("JetPackLog")
            resultState.paresException(it)
        }
    }
}

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMessage: String =  getString(R.string.reqing)
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t -> success(t) }
            }.onFailure { e ->
                //打印错误消息
                e.message?.loge("JetPackLog")
                //失败回调
                error(ExceptionHandle.handleException(e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge("JetPackLog")
            //失败回调
            error(ExceptionHandle.handleException(it))
        }
    }
}

/**
 *  不过滤请求结果
 * @param block 请求体 必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不给
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.requestNoCheck(
    block: suspend () -> T,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMessage: String =  getString(R.string.reqing)
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
    return viewModelScope.launch {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            //成功回调
            success(it)
        }.onFailure {
            //网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge("JetPackLog")
            it.message?.tl()
            //失败回调
            error(ExceptionHandle.handleException(it))
            if (((it is HttpException) && it.code() == 401)) {
                App.instance.cleanStartLogin()
            }
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        if (response.isSucces()) success(response.getResponseData())
        else throw AppException(
            response.getResponseCode(),
            response.getResponseMsg(),
            response.getResponseMsg()
        )
    }
}

/**
 *  调用携程
 * @param block 操作耗时操作任务
 * @param success 成功回调
 * @param error 失败回调 可不给
 */
fun <T> BaseViewModel.launch(
    block: (CoroutineScope) -> T,
    success: (T) -> Unit,
    error: (Throwable) -> Unit = {}
) {
    viewModelScope.launch {
        kotlin.runCatching {
            withContext(Dispatchers.IO) {
                block(this)
            }
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}
