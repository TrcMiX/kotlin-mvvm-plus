package com.mvvm.plus.support.util

import android.graphics.Paint
import android.widget.TextView
import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.TimeUtils
import com.mvvm.plus.App
import com.mvvm.plus.R


object StringUtil {
    fun getDateStr(seconds: Int): String? {
        val h = seconds / 3600
        val m = seconds % 3600 / 60
        val s = seconds % 3600 % 60
        var str = ""
        if (h > 0)
            str = str.plus(h.toString() + App.instance.getString(R.string.hours))
        if (m > 0)
            str = str.plus(m.toString() + App.instance.getString(R.string.minutes))
        if (s > 0)
            str = str.plus(s.toString() + App.instance.getString(R.string.seconds))
        return str
    }

    fun getDate(seconds: Int, totalSec: Int): String? {
        val h = TimeUtils.getTimeSpan(seconds.toLong(), 0, TimeConstants.HOUR)
        val m = TimeUtils.getTimeSpan(seconds.toLong(), 0, TimeConstants.MIN)
        val s = TimeUtils.getTimeSpan(seconds.toLong(), 0, TimeConstants.SEC)
        val ht = totalSec / 3600
        val mt = totalSec % 3600 / 60
        val st = totalSec % 3600 % 60
        if (ht > 0)
            return h.toString()
        if (mt > 0)
            return m.toString()
        if (st > 0)
            return s.toString()
        return ""
    }

    private fun autoSplitText(tv: TextView): String? {
        val rawText = tv.text.toString() //原始文本
        val tvPaint: Paint = tv.paint //paint，包含字体等信息
        val tvWidth = (tv.width - tv.paddingLeft - tv.paddingRight).toFloat() //控件可用宽度
        //将原始文本按行拆分
        val rawTextLines = rawText.replace("\r".toRegex(), "").split("\n".toRegex()).toTypedArray()
        val sbNewText = StringBuilder()
        for (rawTextLine in rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine)
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                var lineWidth = 0f
                var cnt = 0
                while (cnt != rawTextLine.length) {
                    val ch = rawTextLine[cnt]
                    lineWidth += tvPaint.measureText(ch.toString())
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch)
                    } else {
                        sbNewText.append("\n")
                        lineWidth = 0f
                        --cnt
                    }
                    ++cnt
                }
            }
            sbNewText.append("\n")
        }
        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length - 1)
        }
        return sbNewText.toString()
    }
}
