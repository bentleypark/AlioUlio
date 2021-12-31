package com.alio.ulio.ui.alarm.date

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.alio.ulio.R
import com.alio.ulio.databinding.CalendarDayLayoutBinding
import com.alio.ulio.databinding.FragmentAlarmDaySelectionBinding
import com.alio.ulio.ext.daysOfWeekFromLocale
import com.alio.ulio.ui.alarm.MakeAlarmViewModel
import com.alio.ulio.ui.base.BaseViewBindingFragment
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.kizitonwose.calendarview.utils.yearMonth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class OneTimeAlarmDateTimeSelectionFragment :
    BaseViewBindingFragment<FragmentAlarmDaySelectionBinding>(R.layout.fragment_alarm_day_selection) {

    private val activityViewModel: MakeAlarmViewModel by activityViewModels()

    private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

    private val dateFormatter = DateTimeFormatter.ofPattern("DD")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUi()
    }

    private fun setUi() {
        activityViewModel.setTitle("알람의 조건을\n설정하세요")

        observeViewModel()
        setUiOfCalendarView()
        setUIAndEventOfCalendarViewHeader()
        setBtnListener()
        setUiOfBtnNext()
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

    private fun setUiOfBtnNext() = with(activityViewModel) {
        setBtnNextAction {
            findNavController().navigate(R.id.action_alarmDateTimeSelectionFragment_to_recordAlarmFragment)
        }

        setProgressImg(R.drawable.ic_progress_line01)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.selectedDate.collect { selectedDate ->
                    selectedDate?.let {
                        val fullDateText =
                            "${selectedDate.yearMonth.year}월 ${monthFormatter.format(selectedDate.yearMonth)} ${selectedDate.dayOfMonth}일"
                        viewBinding.tvHint.text = fullDateText
                        viewBinding.tvHint.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.selected_date_color
                            )
                        )
                        viewBinding.expandLayout.collapse(true)
                    }
                }
            }
        }
    }

    private fun setUIAndEventOfCalendarViewHeader() = with(viewBinding) {
        calendarView.monthScrollListener = { month ->
            val monthText = "${month.yearMonth.year} ${monthFormatter.format(month.yearMonth)}"
            tvMonth.text = monthText
        }

        leftArrow.setOnClickListener {
            calendarView.findFirstVisibleMonth()?.let {
                calendarView.smoothScrollToMonth(it.yearMonth.previous)
            }
        }

        rightArrow.setOnClickListener {
            calendarView.findFirstVisibleMonth()?.let {
                calendarView.smoothScrollToMonth(it.yearMonth.next)
            }
        }
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
                    with(container.binding) {
                        container.day = day

                        if (day.owner == DayOwner.NEXT_MONTH ||
                            day.owner == DayOwner.PREVIOUS_MONTH
                        ) {
                            root.alpha = 0.0f
                        } else {
                            root.alpha = 1.0f

                            root.setOnClickListener {
                                val selectedDate = activityViewModel.selectedDate.value
                                if (selectedDate != day.date) {
                                    activityViewModel.setSelectedDate(day.date)
                                    calendarView.notifyDateChanged(day.date)
                                    selectedDate?.let {
                                        calendarView.notifyDateChanged(it)
                                    }
                                }
                            }
                        }

                        val textView = dayText
                        textView.text = day.date.dayOfMonth.toString()

                        if (day.owner == DayOwner.THIS_MONTH) {
                            if (activityViewModel.selectedDate.value == day.date) {
                                selectedDateView.isVisible = true
                                textView.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white
                                    )
                                )
                            } else {
                                selectedDateView.isVisible = false
                                textView.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.calendar_black
                                    )
                                )
                            }
                        }

                    }

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

    class DayViewContainer(
        view: View
    ) : ViewContainer(view) {
        lateinit var day: CalendarDay
        val binding = CalendarDayLayoutBinding.bind(view)
    }

    class MonthViewContainer(view: View) : ViewContainer(view) {
//    val textView = ViewCalendarHeaderBinding.bind(view).tvMonth
    }
}

