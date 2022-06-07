package com.org.utilspack.callback.databind

import androidx.databinding.ObservableField

/**
 * 作者　: TrcMiX
 * 描述　:自定义的 Byte 类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class ByteObservableField(value: Byte = 0) : ObservableField<Byte>(value) {

    override fun get(): Byte {
        return super.get()!!
    }

}