package com.mvvm.plus.support.util

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import com.mvvm.utilspack.base.appContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * 作者　: TrcMiX
 * 描述　: 时间工具类
 */

object DatetimeUtil {

    val DATE_PATTERN = "yyyy-MM-dd"
    var DATE_PATTERN_SS = "yyyy-MM-dd HH:mm:ss"
    var DATE_PATTERN_MM = "yyyy-MM-dd HH:mm"

    /**
     * 获取现在时刻
     */
    val now: Date
        get() = Date(Date().time)

    /**
     * 获取现在时刻
     */
    val nows: Date
        get() = formatDate(DATE_PATTERN, now)

    /**
     * Date to Strin
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date?, formatStyle: String): String {
        return if (date != null) {
            val sdf = SimpleDateFormat(formatStyle)
            sdf.timeZone = TimeZone.getDefault()
            sdf.format(date)
        } else {
            ""
        }

    }

    /**
     * Date to Strin
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Long, formatStyle: String): String {
        val sdf = SimpleDateFormat(formatStyle)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(date))

    }

    fun formatDate(formatStyle: String, formatStr: String): Date {
        val resources: Resources = appContext.resources
        val config: Configuration = resources.configuration
        val sdf = SimpleDateFormat(formatStyle, config.locale)
        sdf.timeZone = TimeZone.getDefault()
        return try {
            val date = Date()
            date.time = sdf.parse(formatStr).time
            date
        } catch (e: Exception) {
            println(e.message)
            nows
        }

    }

    /**
     * Date to Date
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDate(formatStyle: String, date: Date?): Date {
        if (date != null) {
            val sdf = SimpleDateFormat(formatStyle)
            sdf.timeZone = TimeZone.getDefault()
            val formatDate = sdf.format(date)
            try {
                return sdf.parse(formatDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                return Date()
            }

        } else {
            return Date()
        }
    }

    /**
     * 将时间戳转换为时间
     */
    fun stampToDate(s: String): Date {
        val lt = s.toLong()
        return Date(lt)
    }

    /**
     * 获得指定时间的日期
     */
    fun getCustomTime(dateStr: String): Date {
        return formatDate(DATE_PATTERN, dateStr)
    }

    fun getWeekName(date: Date): String {
        val string = SPUtils.getInstance().getString("KEY_LOCALE")
        when (string) {
            Locale.SIMPLIFIED_CHINESE.country -> return TimeUtils.getChineseWeek(date)
            Locale.ENGLISH.country -> return TimeUtils.getUSWeek(date)
        }
        return TimeUtils.getChineseWeek(date)
    }

    /**
     *
     * Description: 本地时间转化为UTC时间
     * @param localTime
     * @return
     */
    fun localToUTC(formatStyle: String, localTime: String?): Date? {
        val sdf = SimpleDateFormat(formatStyle)
        var localDate: Date? = null
        try {
            localDate = sdf.parse(localTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val localTimeInMillis = localDate!!.time

        /** long时间转换成Calendar  */
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = localTimeInMillis
        /** 取得时间偏移量  */
        val zoneOffset = calendar[Calendar.ZONE_OFFSET]

        /** 取得夏令时差  */
        val dstOffset = calendar[Calendar.DST_OFFSET]
        /** 从本地时间里扣除这些差量，即可以取得UTC时间 */
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
        return Date(calendar.timeInMillis)
    }

    /**
     *
     * Description:UTC时间转化为本地时间
     * @param utcTime
     * @return
     */
    fun utcToLocal(formatStyle: String, utcTime: String?): Date {
        val sdf = SimpleDateFormat(formatStyle)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        var utcDate: Date? = null
        try {
            utcDate = sdf.parse(utcTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        sdf.timeZone = TimeZone.getDefault()
        var localDate = Date()
        val localTime = sdf.format(utcDate!!.time)
        try {
            localDate = sdf.parse(localTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return localDate
    }
}
