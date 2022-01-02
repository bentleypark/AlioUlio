package com.alio.ulio.ui.alarm.alarm_condition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.alio.ulio.R
import com.alio.ulio.databinding.FragmentRegularAlarmDateTimeSelectionBinding
import com.alio.ulio.databinding.ViewItemPeriodBinding
import com.alio.ulio.ui.alarm.MakeAlarmViewModel
import com.alio.ulio.ui.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class RegularAlarmDateTimeSelectionFragment :
    BaseViewBindingFragment<FragmentRegularAlarmDateTimeSelectionBinding>(
        R.layout.fragment_regular_alarm_date_time_selection
    ) {

    private val viewModel: RegularAlarmDateTimeSelectionViewModel by viewModels()
    private val activityViewModel: MakeAlarmViewModel by activityViewModels()

    private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

    private lateinit var daySelectionAdapter: AlarmDaySelectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setUi()
    }

    private fun setUi() {
        activityViewModel.setTitle("알람의 조건을\n설정하세요")
        activityViewModel.setProgressImg(R.drawable.ic_progress_line01)

        setDayRecyclerview()
        setUiOfBtnNext()
    }

    private fun setDayRecyclerview() = with(viewBinding.rvDay) {
        daySelectionAdapter = AlarmDaySelectionAdapter {
            viewModel.updateAlarmDayList(it)
        }
        adapter = daySelectionAdapter
        setHasFixedSize(true)
        addItemDecoration(SpaceDecoration(5))
    }

    private fun setUiOfBtnNext() = with(activityViewModel) {
        setBtnNextAction {
            findNavController().navigate(R.id.action_regularAlarmDateTimeSelectionFragment_to_recordAlarmFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.selectedAlarmDay.observe(viewLifecycleOwner) { alarmDayUiList ->
            if (!alarmDayUiList.isNullOrEmpty()) {
                viewBinding.expandPeriod.expand()
                "(매주) ${viewModel.convertDayOfWeek()}".also { viewBinding.tvAlarmPeriod.text = it }
            } else {
                viewBinding.expandPeriod.collapse()
                viewBinding.tvAlarmPeriod.text = null
            }
        }
    }
}


class AlarmDaySelectionAdapter(private val optionClickEvent: (AlarmDayUi) -> Unit) :
    RecyclerView.Adapter<AlarmDaySelectionAdapter.AlarmDaySelectionViewHolder>() {

    private lateinit var itemPeriodBinding: ViewItemPeriodBinding

    private val datList = listOf(
        AlarmDayUi(AlarmDay.MONDAY),
        AlarmDayUi(AlarmDay.TUESDAY),
        AlarmDayUi(AlarmDay.WEDNESDAY),
        AlarmDayUi(AlarmDay.THURSDAY),
        AlarmDayUi(AlarmDay.FRIDAY),
        AlarmDayUi(AlarmDay.SATURDAY),
        AlarmDayUi(AlarmDay.SUNDAY)
    )

    class AlarmDaySelectionViewHolder(
        private val itemPeriodBinding: ViewItemPeriodBinding,
        private val optionClickEvent: (AlarmDayUi) -> Unit
    ) :
        RecyclerView.ViewHolder(itemPeriodBinding.root) {
        fun binding(alarmDayUi: AlarmDayUi) {
            with(itemPeriodBinding) {
                tvDay.text = alarmDayUi.alarmDay.dayOfWeekString
                ivCheck.isSelected = alarmDayUi.isSelected
                itemRoot.isSelected = alarmDayUi.isSelected
                itemRoot.setOnClickListener {

                    optionClickEvent.invoke(alarmDayUi)

                    ivCheck.isSelected = !ivCheck.isSelected
                    itemRoot.isSelected = !itemRoot.isSelected
                    tvDay.isSelected = !tvDay.isSelected
                    if (!tvDay.isSelected) {
                        tvDay.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                R.color.selected_date_color
                            )
                        )
                    } else {
                        tvDay.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                R.color.input_text_color
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmDaySelectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        itemPeriodBinding = ViewItemPeriodBinding.inflate(inflater)
        return AlarmDaySelectionViewHolder(itemPeriodBinding, optionClickEvent)
    }

    override fun onBindViewHolder(holder: AlarmDaySelectionViewHolder, position: Int) {
        val item = datList[position]
        holder.binding(item)
    }

    override fun getItemCount() = datList.size
}


data class AlarmDayUi(
    val alarmDay: AlarmDay,
    val isSelected: Boolean = false
)

enum class AlarmDay(
    val dayOfWeek: Int,
    val dayOfWeekString: String
) {
    MONDAY(1, "월요일"),
    TUESDAY(2, "화요일"),
    WEDNESDAY(3, "수요일"),
    THURSDAY(4, "목요일"),
    FRIDAY(5, "금요일"),
    SATURDAY(6, "토요일"),
    SUNDAY(7, "일요일")
}