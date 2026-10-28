package com.example.helloworld

data class CalendarDay(
    val day: Int,
    val isCurrentMonth: Boolean,
    val lunarDay: String,
    val solarDate: String,
    val note: String
)

object LunarCalendarUtils {

    fun buildMonthDays(year: Int, month: Int): List<CalendarDay> {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)

        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1 = Sunday
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val result = mutableListOf<CalendarDay>()

        val prevMonthCalendar = Calendar.getInstance()
        prevMonthCalendar.set(year, month, 1)
        prevMonthCalendar.add(Calendar.MONTH, -1)
        val prevMonthDays = prevMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val offset = firstDayOfWeek - 1

        for (i in 0 until 42) {
            val dayNumber: Int
            val isCurrentMonth: Boolean

            when {
                i < offset -> {
                    dayNumber = prevMonthDays - offset + i + 1
                    isCurrentMonth = false
                }
                i >= offset + daysInMonth -> {
                    dayNumber = i - (offset + daysInMonth) + 1
                    isCurrentMonth = false
                }
                else -> {
                    dayNumber = i - offset + 1
                    isCurrentMonth = true
                }
            }

            val lunarDay = when {
                dayNumber % 2 == 0 -> "${dayNumber / 2 + 1}"
                else -> "${(dayNumber + 1) / 2}"
            }

            val note = when {
                dayNumber == 1 -> "Mùng 1 âm lịch"
                dayNumber == 15 -> "Rằm"
                dayNumber % 10 == 0 -> "Ngày tốt"
                else -> "Ngày thường"
            }

            result.add(
                CalendarDay(
                    day = dayNumber,
                    isCurrentMonth = isCurrentMonth,
                    lunarDay = lunarDay,
                    solarDate = "$dayNumber/${month + 1}/$year",
                    note = note
                )
            )
        }

        return result
    }
}
