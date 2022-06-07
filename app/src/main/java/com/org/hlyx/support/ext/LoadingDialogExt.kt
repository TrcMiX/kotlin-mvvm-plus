package com.org.hlyx.support.ext

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.org.hlyx.R
import razerdp.basepopup.BasePopupWindow

/**
 * @author :TrcMiX
 *
 */

//loading框
private var loadingDialog: BasePopupWindow? = null

/**
 * 打开等待框
 */
fun AppCompatActivity.showLoadingExt(message: String = getString(R.string.request_network)) {
    if (!this.isFinishing) {
        if (loadingDialog == null) {
            loadingDialog = object : BasePopupWindow(this) {
                override fun onCreateContentView(): View {
                    return createPopupById(R.layout.layout_custom_progress_dialog_view).apply {
                        this.findViewById<TextView>(R.id.loading_tips).text = message
                        isOutSideTouchable = false
                        bindLifecycleOwner(this@showLoadingExt)
                        popupGravity = Gravity.CENTER
                        setBackPressEnable(false)
                    }
                }
            }
        }
        loadingDialog?.showPopupWindow()
    }
}


/**
 * 打开等待框
 */
fun Fragment.showLoadingExt(message: String = getString(R.string.request_network)) {
    activity?.let {
        if (!it.isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = object : BasePopupWindow(it) {
                    override fun onCreateContentView(): View {
                        return createPopupById(R.layout.layout_custom_progress_dialog_view).apply {
                            this.findViewById<TextView>(R.id.loading_tips).text = message
                            isOutSideTouchable = false
                            bindLifecycleOwner(this@showLoadingExt)
                            popupGravity = Gravity.CENTER
                            setBackPressEnable(false)
                        }
                    }
                }
            }
        }
        loadingDialog?.showPopupWindow()
    }

}

/**
 * 关闭等待框
 */
fun Activity.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}

/**
 * 关闭等待框
 */
fun Fragment.dismissLoadingExt() {
    loadingDialog?.dismiss()
    loadingDialog = null
}
