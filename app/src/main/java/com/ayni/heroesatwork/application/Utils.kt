package com.ayni.heroesatwork.application

import android.content.Context
import java.util.*
import android.util.TypedValue
import android.content.Context.INPUT_METHOD_SERVICE





class DateUtils {
    companion object {
        fun getStartOfDay(date: Date) : Date {
            var calendar = GregorianCalendar()
            calendar.time = date
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DATE)

            calendar.set(year, month, day, 0, 0, 0)
            return calendar.getTime()
        }

        fun getEndOfDay(date: Date) : Date {
            var calendar = GregorianCalendar()
            calendar.time = date
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DATE)

            calendar.set(year, month, day, 23, 59, 59)
            return calendar.getTime()
        }
    }

}
