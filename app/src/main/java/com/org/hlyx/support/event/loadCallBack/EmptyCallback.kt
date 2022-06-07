package com.org.hlyx.support.event.loadCallBack


import com.kingja.loadsir.callback.Callback
import com.org.hlyx.R


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}