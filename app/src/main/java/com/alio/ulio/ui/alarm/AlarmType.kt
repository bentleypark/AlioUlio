package com.alio.ulio.ui.alarm

data class AlarmSelectionType(
    val type: AlarmType,
    val titleImg: Int,
    val image: Int
)

sealed class AlarmType(val typeName: String) {
    object Onetime : AlarmType("onetime")
    object Regular : AlarmType("regular")
}
