package com.example.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvMonthTitle: TextView
    private lateinit var tvSelectedDate: TextView
    private lateinit var tvLunarDate: TextView
    private lateinit var tvNote: TextView
    private lateinit var btnPrevMonth: Button
    private lateinit var btnNextMonth: Button

    private var currentYear = Calendar.getInstance().get(Calendar.YEAR)
    private var currentMonth = Calendar.getInstance().get(Calendar.MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerCalendar)
        tvMonthTitle = findViewById(R.id.tvMonthTitle)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvLunarDate = findViewById(R.id.tvLunarDate)
        tvNote = findViewById(R.id.tvNote)
        btnPrevMonth = findViewById(R.id.btnPrevMonth)
        btnNextMonth = findViewById(R.id.btnNextMonth)

        recyclerView.layoutManager = GridLayoutManager(this, 7)

        btnPrevMonth.setOnClickListener {
            currentMonth--
            normalizeMonth()
            renderMonth()
        }

        btnNextMonth.setOnClickListener {
            currentMonth++
            normalizeMonth()
            renderMonth()
        }

        renderMonth()
    }

    private fun normalizeMonth() {
        while (currentMonth < 0) {
            currentMonth += 12
            currentYear--
        }
        while (currentMonth > 11) {
            currentMonth -= 12
            currentYear++
        }
    }

    private fun renderMonth() {
        val monthDays = LunarCalendarUtils.buildMonthDays(currentYear, currentMonth)
        tvMonthTitle.text = "Tháng ${currentMonth + 1}/$currentYear"

        val adapter = DayAdapter(monthDays) { selectedDay ->
            tvSelectedDate.text = "Ngày ${selectedDay.day}/${currentMonth + 1}/$currentYear"
            tvLunarDate.text = "Âm lịch: ${selectedDay.lunarDay}"
            tvNote.text = selectedDay.note
        }

        recyclerView.adapter = adapter

        val today = Calendar.getInstance()
        val todayIndex = monthDays.indexOfFirst {
            it.isCurrentMonth &&
                it.day == today.get(Calendar.DAY_OF_MONTH) &&
                today.get(Calendar.YEAR) == currentYear &&
                today.get(Calendar.MONTH) == currentMonth
        }

        if (todayIndex != -1) {
            val todayDay = monthDays[todayIndex]
            tvSelectedDate.text = "Ngày ${todayDay.day}/${currentMonth + 1}/$currentYear"
            tvLunarDate.text = "Âm lịch: ${todayDay.lunarDay}"
            tvNote.text = todayDay.note
        } else {
            val firstValid = monthDays.firstOrNull { it.isCurrentMonth }
            if (firstValid != null) {
                tvSelectedDate.text = "Ngày ${firstValid.day}/${currentMonth + 1}/$currentYear"
                tvLunarDate.text = "Âm lịch: ${firstValid.lunarDay}"
                tvNote.text = firstValid.note
            }
        }
    }
}
