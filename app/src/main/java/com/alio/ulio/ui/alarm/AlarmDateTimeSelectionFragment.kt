package com.alio.ulio.ui.alarm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alio.ulio.R
import com.alio.ulio.databinding.CalendarDayLayoutBinding
import com.alio.ulio.databinding.FragmentAlarmDaySelectionBinding
import com.alio.ulio.databinding.ViewCalendarHeaderBinding
import com.alio.ulio.ext.daysOfWeekFromLocale
import com.alio.ulio.ui.base.BaseViewBindingFragment
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.YearMonth

@AndroidEntryPoint
class AlarmDateTimeSelectionFragment :
    BaseViewBindingFragment<FragmentAlarmDaySelectionBinding>(R.layout.fragment_alarm_day_selection) {

    private val activityViewModel: MakeAlarmViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUi()
    }

    private fun setUi() {
        activityViewModel.setTitle("알람의 조건을\n설정하세요")
        setUiOfCalendarView()
        setBtnListener()
        setProgressAnim()
    }

    private fun setBtnListener() = with(viewBinding) {
        btnCalendar.setOnClickListener {
            if (expandLayout.isExpanded) {
                expandLayout.collapse(true)
            } else {
                expandLayout.expand(true)
            }
        }
    }

    private fun setProgressAnim() {
        activityViewModel.setProgressAnim("indicator.json")
    }

    private fun setUiOfCalendarView() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            val daysOfWeek = daysOfWeekFromLocale()

            val currentMonth = YearMonth.now()
            val calendarView = viewBinding.calendarView
            calendarView.setup(
                currentMonth.minusMonths(10),
                currentMonth.plusMonths(10),
                daysOfWeek.first()
            )
            calendarView.scrollToMonth(currentMonth)
            calendarView.dayBinder = object : DayBinder<DayViewContainer> {
                override fun bind(container: DayViewContainer, day: CalendarDay) {
                    container.day = day
                    val textView = container.binding.dayText
                    val layout = container.binding.exFiveDayLayout
                    textView.text = day.date.dayOfMonth.toString()
                }

                override fun create(view: View) = DayViewContainer(view)
            }
            calendarView.monthHeaderBinder = object :
                MonthHeaderFooterBinder<MonthViewContainer> {
                override fun bind(container: MonthViewContainer, month: CalendarMonth) {

                }

                override fun create(view: View) = MonthViewContainer(view)

            }
        }
    }
}

class DayViewContainer(
    view: View
) : ViewContainer(view) {
    lateinit var day: CalendarDay
    val binding = CalendarDayLayoutBinding.bind(view)
}

class MonthViewContainer(view: View) : ViewContainer(view) {
//    val textView = ViewCalendarHeaderBinding.bind(view).tvMonth
}