package com.alio.ulio.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alio.ulio.R
import com.alio.ulio.ui.theme.AccountToolbarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun Account() {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(AccountToolbarColor)
    }

    Scaffold(
        topBar = { Toolbar() }) {

    }
}

@Composable
private fun Toolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_profile_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Image(
                painter = painterResource(R.drawable.ic_account_label),
                contentDescription = null,
                modifier = Modifier.padding(start = 20.dp, top = 24.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_agr),
                contentDescription = null,
                modifier = Modifier.padding(end = 20.dp, top = 24.dp).align(Alignment.TopEnd)
            )
            Icon(
                painter = painterResource(R.drawable.ic_faq),
                contentDescription = null,
                modifier = Modifier.padding(end = 70.dp, top = 24.dp).align(Alignment.TopEnd)
            )
        }
    }
}