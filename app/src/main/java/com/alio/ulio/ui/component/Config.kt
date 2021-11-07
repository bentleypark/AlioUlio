package com.alio.ulio.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val verticalPadding = 12.dp
val horizontalPadding = 10.dp
val bottomBarHeight = 70.dp

fun Modifier.icon() = padding(bottom = 7.dp)

fun Modifier.defaultPadding() = this.padding(
    horizontal = horizontalPadding,
    vertical = verticalPadding
)