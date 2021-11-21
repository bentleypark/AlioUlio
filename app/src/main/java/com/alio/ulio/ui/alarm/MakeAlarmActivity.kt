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


        val daysOfWeek = daysOfWeekFromLocale()
//
//        val currentMonth = YearMonth.now()
//        val calendarView = viewBinding.calendarView
//        calendarView.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
//        calendarView.scrollToMonth(currentMonth)
//        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
//            override fun bind(container: DayViewContainer, day: CalendarDay) {
//
//            }
//
//            override fun create(view: View) = DayViewContainer(view)
//        }
//        calendarView.monthHeaderBinder = object :
//            MonthHeaderFooterBinder<MonthViewContainer> {
//            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
//
//            }
//
//            override fun create(view: View) = MonthViewContainer(view)
//
//        }
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

class DayViewContainer(
    view: View
) : ViewContainer(view) {
    val binding = CalendarDayLayoutBinding.bind(view)
}

class MonthViewContainer(view: View) : ViewContainer(view) {
    val textView = ViewCalendarHeaderBinding.bind(view).tvMonth
}