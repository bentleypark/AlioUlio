package com.alio.ulio.ui.alarm

import com.alio.ulio.R

const val stage_1_image = R.drawable.ic_alarm_stage_1
const val stage_2_image = R.drawable.ic_alarm_stage_2

fun getStageImage(stage: Int): Int {
    return when (stage) {
        1 -> stage_1_image
        2 -> stage_2_image
        else -> 0
    }
}