package com.mvvm.plus.support.event

import com.mvvm.plus.data.model.DataBean
import com.mvvm.utilspack.base.viewmodel.BaseViewModel
import com.mvvm.utilspack.callback.livedata.event.EventLiveData


/**
 * 作者　: TrcMiX
 * 描述　:APP全局的ViewModel，可以在这里发送全局通知替代EventBus，LiveDataBus等
 */
class EventViewModel : BaseViewModel() {

    //全局收藏，在任意一个地方收藏或取消收藏，监听该值的界面都会收到消息
    val collectEvent = EventLiveData<DataBean.CollectBus>()

    //分享文章通知
    val shareArticleEvent = EventLiveData<Boolean>()

    //添加TODO通知
    val todoEvent = EventLiveData<Boolean>()

}