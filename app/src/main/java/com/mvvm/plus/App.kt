package com.mvvm.plus

import androidx.multidex.MultiDex
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.blankj.utilcode.util.LanguageUtils
import com.blankj.utilcode.util.SPUtils
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.mvvm.plus.support.event.loadCallBack.EmptyCallback
import com.mvvm.plus.support.event.loadCallBack.ErrorCallback
import com.mvvm.plus.support.event.loadCallBack.LoadingCallback
import com.mvvm.plus.support.util.SettingUtil
import com.mvvm.plus.support.util.SettingUtil.needSwitchLanguage
import com.mvvm.utilspack.base.BaseApp
import com.mvvm.utilspack.ext.util.jetPackLog
import com.tencent.mmkv.MMKV


/**
 * 作者　: TrcMiX
 * 描述　:
 */

class App : BaseApp() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()
        jetPackLog = BuildConfig.DEBUG

        val string = SPUtils.getInstance().getString("KEY_LOCALE")
        if (needSwitchLanguage(this, string)) {
            LanguageUtils.applyLanguage(SettingUtil.getLanguageLocale(string))
        }

        //防止项目崩溃，崩溃后打开错误界面
        if (!BuildConfig.DEBUG)
            CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(true)//是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
                .showErrorDetails(false) //是否必须显示包含错误详细信息的按钮 default: true
                .showRestartButton(false) //是否必须显示“重新启动应用程序”按钮或“关闭应用程序”按钮default: true
                .logErrorOnRestart(false) //是否必须重新堆栈堆栈跟踪 default: true
                .trackActivities(true) //是否必须跟踪用户访问的活动及其生命周期调用 default: false
                .minTimeBetweenCrashesMs(2000) //应用程序崩溃之间必须经过的时间 default: 3000
//                .restartActivity(LoginActivity::class.java) // 重启的activity
//                .errorActivity(LoginActivity::class.java) //发生错误跳转的activity
                .eventListener(null) //允许你指定事件侦听器，以便在库显示错误活动 default: null
                .apply()
    }


}
