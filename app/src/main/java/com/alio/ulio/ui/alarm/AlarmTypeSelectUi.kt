package com.alio.ulio.ui.alarm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alio.ulio.R
import com.alio.ulio.ui.alarm.MakeAlarmActivity.Companion.newIntent
import com.alio.ulio.ui.theme.AlarmTypSelectionColor
import com.alio.ulio.ui.theme.AlioUlioTheme
import com.alio.ulio.ui.theme.ToolbarTitleColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AlarmTypeSelectionUi(navController: NavController) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(Color.White)
    }

    Scaffold(
        topBar = { AlarmUiToolbar(false, "어떤 알람을\n보내실건가요?") }) {
        AlarmTypeSelectList()
    }
}

@Composable
fun AlarmUiToolbar(backButtonYn: Boolean, title: String) {
    Column {
        AnimatedVisibility(visible = backButtonYn) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp)
        )
//        Image(
//            painter = painterResource(getStageImage(stage)),
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .padding(start = 20.dp, top = 40.dp)
//        )
    }
}

@Composable
fun AlarmTypeSelectList() {

    val alarmTypeList = listOf(
        AlarmSelectionType(
            AlarmType.Onetime,
            R.drawable.ic_alarm_type_one_time_title,
            R.drawable.ic_alarm_type_one_time
        ),
        AlarmSelectionType(
            AlarmType.Regular,
            R.drawable.ic_alarm_type_regular_title,
            R.drawable.ic_alarm_type_regular
        )
    )
    val context = LocalContext.current

    LazyColumn {
        itemsIndexed(alarmTypeList) { index, alarm ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(40.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(start = 20.dp, end = 20.dp)
                    .background(color = AlarmTypSelectionColor, shape = RoundedCornerShape(10.dp))
                    .clickable {
                        when (alarm.type) {
                            AlarmType.Onetime -> {
                                context.startActivity(
                                    newIntent(
                                        context,
                                        AlarmType.Onetime.typeName
                                    )
                                )
                            }
                            else -> {
                                context.startActivity(
                                    newIntent(
                                        context,
                                        AlarmType.Regular.typeName
                                    )
                                )
                            }
                        }
                    }
            ) {
                Image(
                    painter = painterResource(alarm.titleImg),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp)
                )
                Image(
                    painter = painterResource(alarm.image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 30.dp, bottom = 20.dp, end = 20.dp)
                        .align(Alignment.Bottom)

                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (index == 1) {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

