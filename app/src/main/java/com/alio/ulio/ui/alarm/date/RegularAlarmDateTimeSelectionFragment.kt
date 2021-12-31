package com.alio.ulio.ui.alarm.date

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.alio.ulio.R
import com.alio.ulio.databinding.FragmentRegularAlarmDateTimeSelectionBinding
import com.alio.ulio.ui.alarm.MakeAlarmViewModel
import com.alio.ulio.ui.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class RegularAlarmDateTimeSelectionFragment : BaseViewBindingFragment<FragmentRegularAlarmDateTimeSelectionBinding>(
    R.layout.fragment_regular_alarm_date_time_selection) {

    private val viewModel: RegularAlarmDateTimeSelectionViewModel by viewModels()
    private val activityViewModel: MakeAlarmViewModel by activityViewModels()

    private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUi() {
        activityViewModel.setTitle("알람의 조건을\n설정하세요")
        activityViewModel.setProgressImg(R.drawable.ic_progress_line01)
    }

}