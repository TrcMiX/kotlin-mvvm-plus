package com.mvvm.plus.data.model.response

import java.io.Serializable

/**
 *  分页数据的基类
 */
data class ApiPagerResponse<T>(
    var datas: T,
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
) : Serializable {
    /**
     * 数据是否为空
     */
    fun isEmpty(): Boolean {

        return (datas as List<*>).size == 0
    }

    /**
     * 是否为刷新
     */
    fun isRefresh(): Boolean {
        return offset == 0
    }

    /**
     * 是否还有更多数据
     */
    fun hasMore(): Boolean {
        return !over
    }
}