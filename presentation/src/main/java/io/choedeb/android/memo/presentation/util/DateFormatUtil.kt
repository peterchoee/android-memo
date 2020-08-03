package io.choedeb.android.memo.presentation.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * 날짜 표현 유틸리티
 *
 * (1) 오늘 날짜 - Write(수정&작성)
 *      ex) 오늘 2020.02.16
 *
 * (2) 지난 날짜 - Main(목록), Detail(상세)
 *      오늘이 맞는 경우 ex) 오후 17:35
 *      오늘이 아닌 경우 ex) 2020.02.17
 */
object DateFormatUtil {

    fun todayFormatDate(): String {
        val now = Date()
        val formatter = SimpleDateFormat("오늘 yyyy.MM.dd", Locale.KOREAN)
        return formatter.format(now)
    }

    @JvmStatic
    fun compareFormatDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN)

        val inputDate = formatter.format(date)
        val todayDate = formatter.format(Date())

        val compareDate = inputDate.compareTo(todayDate)
        // compareDate == 0 -> 오늘
        // compareDate != 0 -> 지난날
        return if (compareDate == 0) {
            val todayFormatter = SimpleDateFormat("aa HH:mm", Locale.KOREAN)
            todayFormatter.format(date)
        } else {
            inputDate
        }
    }
}