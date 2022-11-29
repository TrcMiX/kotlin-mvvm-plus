package com.mvvm.plus.support.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mvvm.plus.support.util.CacheUtil
import com.mvvm.plus.ui.activity.LoginActivity

fun View.clickStart(cls: Class<out Activity>, func: () -> Unit = {}) {
    this.click {
        func.invoke()
        this.context.startActivity(Intent(this.context, cls))
    }
}

fun View.clickStart(cls: Class<out Activity>, options: Bundle, func: () -> Unit = {}) {
    this.click {
        func.invoke()
        var intent = Intent(this.context, cls)
        intent.putExtras(options)
        this.context.startActivity(intent)
    }
}

fun Activity.start(cls: Class<out Activity>, func: () -> Unit = {}) {
    this.startActivity(Intent(this, cls))
    func.invoke()
}

fun Activity.startAndRet(cls: Class<out Activity>, reqId: Int, func: () -> Unit = {}) {
    this.startActivityForResult(Intent(this, cls), reqId)
    func.invoke()
}

fun Context.start(cls: Class<out Activity>, func: () -> Unit = {}) {
    this.startActivity(Intent(this, cls))
    func.invoke()
}

fun Context.cleanStart(cls: Class<out Activity>, func: () -> Unit = {}) {
    val intent = Intent(this, cls)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
    func.invoke()
}

fun Context.cleanStartLogin() {
    CacheUtil.save(false, CacheUtil.LOGIN_CACHE)
    cleanStart(LoginActivity::class.java)
}