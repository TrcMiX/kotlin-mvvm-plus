package com.mvvm.plus.support.event.loadCallBack


import com.kingja.loadsir.callback.Callback
import com.mvvm.plus.R


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}