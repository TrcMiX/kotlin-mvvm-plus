package com.org.hlyx.support.ext.bindadapter

import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.InputType
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.org.hlyx.R
import com.org.hlyx.data.model.ResponseBody
import com.org.hlyx.databinding.ItemLeftRightTextBinding
import com.org.hlyx.databinding.ItemTextBinding
import com.org.hlyx.support.ext.inflate
import com.org.utilspack.ext.view.textString

/**
 * 作者　: TrcMiX
 * 描述　: 自定义 BindingAdapter
 */
object CustomBindAdapter {

    @BindingAdapter(value = ["checkChange"])
    @JvmStatic
    fun checkChange(checkbox: CheckBox, listener: CompoundButton.OnCheckedChangeListener) {
        checkbox.setOnCheckedChangeListener(listener)
    }

    @BindingAdapter(value = ["showPwd"])
    @JvmStatic
    fun showPwd(view: EditText, boolean: Boolean) {
        if (boolean) {
            view.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            view.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        view.setSelection(view.textString().length)
    }

    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun imageUrl(view: ImageView, url: String) {
        Glide.with(view.context.applicationContext)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }

    @BindingAdapter(value = ["circleImageUrl"])
    @JvmStatic
    fun circleImageUrl(view: ImageView, url: String) {
        Glide.with(view.context.applicationContext)
            .load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }

    @BindingAdapter(value = ["circleImageId"])
    @JvmStatic
    fun circleImageId(view: ImageView, @RawRes @DrawableRes @Nullable resourceId: Int) {
        Glide.with(view.context.applicationContext)
            .load(resourceId)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(view)
    }

    @BindingAdapter(value = ["imageBg"])
    @JvmStatic
    fun imageBg(view: ImageView, src: Int) {
        view.setImageResource(src)
    }

//    @BindingAdapter(value = ["colorCircleViewColor"])
//    @JvmStatic
//    fun colorCircleViewColor(view: MyColorCircleView, color: Int) {
//        view.setView(color)
//    }

    @BindingAdapter(value = ["afterTextChanged"])
    @JvmStatic
    fun EditText.afterTextChanged(action: (String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                action(s.toString())
            }
        })
    }

    @BindingAdapter(value = ["viewForString"])
    @JvmStatic
    fun ViewGroup.viewForString(data: List<String>) {
        data.forEach {
            val view = context.inflate(R.layout.item_text)
            DataBindingUtil.bind<ItemTextBinding>(view)
            var dataBinding = DataBindingUtil.getBinding<ItemTextBinding>(view)
            dataBinding?.vm = it
            addView(view)
        }
    }

    @BindingAdapter(value = ["viewFor"])
    @JvmStatic
    fun ViewGroup.viewFor(data: List<ResponseBody.ResLeftRight>) {
        data.forEach {
            val view = context.inflate(R.layout.item_left_right_text)
            DataBindingUtil.bind<ItemLeftRightTextBinding>(view)
            var dataBinding = DataBindingUtil.getBinding<ItemLeftRightTextBinding>(view)
            dataBinding?.vm = it
            addView(view)
        }
    }

    @BindingAdapter(value = ["viewForText"])
    @JvmStatic
    fun ViewGroup.viewForText(data: List<ResponseBody.ResLeftRightText>?) {
        if (data.isNullOrEmpty()) return
        data.forEach {
            var data = ResponseBody.ResLeftRight(it.desc!!, it.descColor!!, "", it.title!!, it.titleColor!!)
            val view = context.inflate(R.layout.item_left_right_text)
            DataBindingUtil.bind<ItemLeftRightTextBinding>(view)
            var dataBinding = DataBindingUtil.getBinding<ItemLeftRightTextBinding>(view)
            dataBinding?.vm = data
            addView(view)
        }
    }

    @BindingAdapter(value = ["myTextColor"])
    @JvmStatic
    fun TextView.myTextColor(src: String?) {
        if (src.isNullOrEmpty()) return
        setTextColor(Color.parseColor(src))
    }

    @BindingAdapter(value = ["htmlText"])
    @JvmStatic
    fun TextView.htmlText(src: String?) {
        if (src.isNullOrEmpty()) return
        text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(src, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(src)
        }
    }
}

