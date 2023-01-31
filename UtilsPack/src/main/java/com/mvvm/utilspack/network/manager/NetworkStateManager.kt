package com.mvvm.utilspack.network.manager

import com.mvvm.utilspack.callback.livedata.event.EventLiveData

/**
 * 作者　: TrcMiX
 * 描述　: 网络变化管理者
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = EventLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}