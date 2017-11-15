package com.ayni.heroesatwork.application

import java.util.*


class DateUtils {
    companion object {
        fun getTodayStartOfDay() : Date {
            return getStartOfDay(Date())
        }

        fun getTodayEndOfDay() : Date {
            return getEndOfDay(Date())
        }

        fun getStartOfDay(date: Date) : Date {
            val calendar = GregorianCalendar()
            calendar.time = date
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DATE)

            calendar.set(year, month, day, 0, 0, 0)
            return calendar.time
        }

        fun getEndOfDay(date: Date) : Date {
            val calendar = GregorianCalendar()
            calendar.time = date
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DATE)

            calendar.set(year, month, day, 23, 59, 59)
            return calendar.time
        }
    }

}
