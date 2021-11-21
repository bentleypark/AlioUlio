package com.alio.ulio.ui.alarm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.alio.ulio.R
import com.alio.ulio.databinding.ActivityMakeAlarmBinding
import com.alio.ulio.databinding.CalendarDayLayoutBinding
import com.alio.ulio.databinding.ViewCalendarDayBinding
import com.alio.ulio.databinding.ViewCalendarHeaderBinding
import com.alio.ulio.ext.daysOfWeekFromLocale
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import java.time.YearMonth

@AndroidEntryPoint
class MakeAlarmActivity : AppCompatActivity() {

    private val viewBinding: ActivityMakeAlarmBinding by lazy {
        ActivityMakeAlarmBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    companion object {
        fun newIntent(
            context: Context,
            alarmType: String
        ): Intent {
            return Intent(context, MakeAlarmActivity::class.java).apply {

            }
        }
    }
}