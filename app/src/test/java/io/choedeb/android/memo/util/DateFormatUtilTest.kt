package io.choedeb.android.memo.util

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateFormatUtilTest {

    private lateinit var dateFormatUtil: DateFormatUtil

    @Before
    fun init() {
        dateFormatUtil = DateFormatUtil
    }

    @Test
    fun todayFormatDate() {
        val result = dateFormatUtil.todayFormatDate()

        val now = Date()
        val formatter = SimpleDateFormat("오늘 yyyy.MM.dd", Locale.KOREAN)
        val formatDate = formatter.format(now)

        assertEquals(formatDate, result)
    }

    @Test
    fun compareFormatDate_todayDate() {
        val result = dateFormatUtil.compareFormatDate(Date())

        //오늘날짜 생성 후 비교
        val now = Date()
        val formatter = SimpleDateFormat("aa HH:mm", Locale.KOREAN)
        val formatDate = formatter.format(now)

        assertEquals(formatDate, result)
    }

    @Test
    fun compareFormatDate_lastedDate() {
        //지난날짜(어제) 생성 후 전달
        val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val yesterdayDate = formatter.format(calendar.time)

        val result = dateFormatUtil.compareFormatDate(formatter.parse(yesterdayDate)!!)

        assertEquals(yesterdayDate, result)
    }
}