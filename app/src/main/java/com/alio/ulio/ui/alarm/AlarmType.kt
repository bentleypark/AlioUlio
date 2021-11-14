package com.alio.ulio.ui.alarm

data class AlarmSelectionType(
    val type: AlarmType,
    val titleImg: Int,
    val image: Int
)

sealed class AlarmType {
    object Onetime : AlarmType()
    object Regular : AlarmType()
}
