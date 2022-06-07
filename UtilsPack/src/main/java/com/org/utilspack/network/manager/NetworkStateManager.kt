package com.org.utilspack.network.manager

import com.org.utilspack.callback.livedata.UnPeekLiveData

/**
 * 作者　: TrcMiX
 * 描述　: 网络变化管理者
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = UnPeekLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}