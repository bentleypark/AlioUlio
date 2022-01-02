package com.alio.ulio.ui.alarm.alarm_condition.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.alio.ulio.R
import com.alio.ulio.databinding.ViewInputTimeBinding

class AlarmTimeInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val viewBinding = ViewInputTimeBinding.inflate(LayoutInflater.from(context), this, true)

    var currentMeridiem = PM

    companion object {
        private const val AM = "AM"
        private const val PM = "PM"
    }

    init {
        setUi()

    }

    private fun setUi() {
        setUiOfToggleView()
    }

    private fun setUiOfToggleView() = with(viewBinding) {
        leftView.setOnClickListener {
            leftView.isSelected = !leftView.isSelected
            currentMeridiem = AM

            if (leftView.isSelected) {
                tvAm.setTextColor(ContextCompat.getColor(context, R.color.selected_date_color))
                tvPm.setTextColor(ContextCompat.getColor(context, R.color.input_text_color))
                rightView.isSelected = false
            } else {
                tvAm.setTextColor(ContextCompat.getColor(context, R.color.input_text_color))
                tvPm.setTextColor(ContextCompat.getColor(context, R.color.selected_date_color))
                rightView.isSelected = true
            }
        }

        rightView.isSelected = true
        rightView.setOnClickListener {
            rightView.isSelected = !rightView.isSelected
            currentMeridiem = PM

            if (rightView.isSelected) {
                leftView.isSelected = false
                tvPm.setTextColor(ContextCompat.getColor(context, R.color.selected_date_color))
                tvAm.setTextColor(ContextCompat.getColor(context, R.color.input_text_color))
            } else {
                leftView.isSelected = true
                tvPm.setTextColor(ContextCompat.getColor(context, R.color.input_text_color))
                tvAm.setTextColor(ContextCompat.getColor(context, R.color.selected_date_color))
            }
        }
    }
}