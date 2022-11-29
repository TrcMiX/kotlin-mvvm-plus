package com.mvvm.plus.support.ext

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kingja.loadsir.core.LoadService
import com.mvvm.plus.App
import com.mvvm.plus.R
import com.mvvm.plus.support.event.loadCallBack.EmptyCallback
import com.mvvm.plus.support.event.loadCallBack.ErrorCallback
import com.mvvm.plus.support.network.stateCallback.ListDataUiState
import com.yanzhenjie.recyclerview.SwipeRecyclerView

/**
 * 描述　:项目中自定义类的拓展函数
 */

/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

/**
 * 加载列表数据
 */
fun <T> loadListData(
    data: ListDataUiState<T>,
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    loadService: LoadService<*>,
    recyclerView: SwipeRecyclerView,
    swipeRefreshLayout: SwipeRefreshLayout
) {
    swipeRefreshLayout.isRefreshing = false
    recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
    if (data.isSuccess) {
        //成功
        when {
            //第一页并没有数据 显示空布局界面
            data.isFirstEmpty -> {
                loadService.showEmpty()
            }
            //是第一页
            data.isRefresh -> {
                baseQuickAdapter.setList(data.listData)
                loadService.showSuccess()
            }
            //不是第一页
            else -> {
                baseQuickAdapter.addData(data.listData)
                loadService.showSuccess()
            }
        }
    } else {
        //失败
        if (data.isRefresh) {
            //如果是第一页，则显示错误界面，并提示错误信息
            loadService.showError(data.errMessage)
        } else {
            recyclerView.loadMoreError(0, data.errMessage)
        }
    }
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}

/**
 * 项目中自定义类的拓展函数
 */

fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_text).text = message
        }
    }
}

fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    overScrollMode = View.OVER_SCROLL_NEVER
    isNestedScrollingEnabled = isScroll
    return this
}

fun String.tl() {
    ToastUtils.showLong(this)
}

fun View.finish() {
    this.setOnClickListener {
        (it.context as Activity).finish()
    }
}

fun View.click(onClick: (it: View) -> Unit = {}) {
    this.setOnClickListener {
        onClick.invoke(it)
    }
}

fun Context.inflate(resId: Int): View {
    return LayoutInflater.from(this).inflate(resId, null)
}

fun upAnimation(): Animation {
    return ScaleAnimation(
        1f, 1f, 0f, 1f,
        Animation.RELATIVE_TO_SELF, 1f,
        Animation.RELATIVE_TO_SELF, 1f
    ).apply {
        duration = 150
    }
}

fun downAnimation(): Animation {
    return ScaleAnimation(
        1f, 1f, 1f, 0f,
        Animation.RELATIVE_TO_SELF, 1f,
        Animation.RELATIVE_TO_SELF, 1f
    ).apply {
        duration = 90
    }
}

fun <T> RecyclerView.loadListDate(
    data: MutableList<T>,
    mAdapter: BaseQuickAdapter<T, BaseViewHolder>,
    mPage: Int
) {
    if (data.size < 20) {
        mAdapter.loadMoreModule.loadMoreEnd()
    } else {
        mAdapter.loadMoreModule.loadMoreComplete()
    }
    if (mPage == 0) {
        init(LinearLayoutManager(this.context), mAdapter, false)
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        mAdapter.setNewInstance(data)
    } else mAdapter.addData(data)
}


fun dpToPx(dp: Float): Int {
    return dpToPx(dp, App.instance.resources)
}

fun dpToPx(dp: Float, resources: Resources): Int {
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    return px.toInt()
}

fun getColorCompat(resId: Int) = ContextCompat.getColor(App.instance, resId)

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}