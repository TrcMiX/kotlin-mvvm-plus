package com.mvvm.utilspack.base.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.mvvm.utilspack.base.viewmodel.BaseViewModel
import com.mvvm.utilspack.ext.inflateBindingWithGeneric

/**
 * 作者　: TrcMiX
 * 描述　: 包含ViewModel 和Databind ViewModelActivity基类，把ViewModel 和Databind注入进来了
 * 需要使用Databind的清继承它
 */
abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>() {

    override fun layoutId() = 0

    lateinit var mDatabind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    /**
     * 创建DataBinding
     */
    override fun initDataBind(): View? {
        mDatabind = inflateBindingWithGeneric(layoutInflater)
        return mDatabind.root
    }
}