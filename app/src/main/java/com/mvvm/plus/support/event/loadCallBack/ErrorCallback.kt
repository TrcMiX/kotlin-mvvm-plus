package com.mvvm.plus.support.event.loadCallBack

import com.kingja.loadsir.callback.Callback
import com.mvvm.plus.R


class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}