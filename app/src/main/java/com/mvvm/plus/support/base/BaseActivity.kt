package com.mvvm.plus.support.base

import android.content.res.Resources
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.mvvm.plus.support.event.AppViewModel
import com.mvvm.plus.support.event.EventViewModel
import com.mvvm.plus.support.ext.dismissLoadingExt
import com.mvvm.plus.support.ext.showLoadingExt
import com.mvvm.utilspack.base.activity.BaseVmDbActivity
import com.mvvm.utilspack.base.viewmodel.BaseViewModel
import com.mvvm.utilspack.ext.getAppViewModel
import me.jessyan.autosize.AutoSizeCompat

/**
 * 作者　: TrcMiX
 * 描述　: 你项目中的Activity基类，在这里实现显示弹窗，吐司，还有加入自己的需求操作 ，如果不想用Databind，请继承
 * BaseVmActivity例如
 * abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>() {
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {

    //Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
    val appViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    //Application全局的ViewModel，用于发送全局通知操作
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

    /**
     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
     */
    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        return super.getResources()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }
}