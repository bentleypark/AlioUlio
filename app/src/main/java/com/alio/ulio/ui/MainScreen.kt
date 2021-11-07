package com.alio.ulio.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alio.ulio.R
import com.alio.ulio.ui.account.Account
import com.alio.ulio.ui.component.bottomBarHeight
import com.alio.ulio.ui.component.icon

@Composable
fun MainScreen() {
    val sectionState = remember { mutableStateOf(BottomNavigationItem.Home) }

    val navItems = BottomNavigationItem.values()
        .toList()
    Scaffold(
        bottomBar = {
            BottomBar(
                items = navItems,
                currentSection = sectionState.value,
                onSectionSelected = { sectionState.value = it },
            )
        }) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Crossfade(
            modifier = modifier,
            targetState = sectionState.value
        )
        { section ->
            when (section) {
                BottomNavigationItem.Home -> Content(title = "Profile")
                BottomNavigationItem.Alarm -> Content(title = "Profile")
                BottomNavigationItem.Account -> Account()
            }
        }
    }
}

@Composable
private fun BottomBar(
    items: List<BottomNavigationItem>,
    currentSection: BottomNavigationItem,
    onSectionSelected: (BottomNavigationItem) -> Unit,
) {
    BottomNavigation(
        modifier = Modifier.height(bottomBarHeight),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.background)
    ) {
        items.forEach { section ->

            val selected = section == currentSection

            val iconRes = if (selected) section.selectedIcon else section.icon

            BottomNavigationItem(
                icon = {
                    Icon(
//                            ImageBitmap.imageResource(id = iconRes),
                        painter = painterResource(id = iconRes),
                        modifier = Modifier.icon(),
                        contentDescription =  section.title
                    )
                },
                selected = selected,
                onClick = { onSectionSelected(section) },
                alwaysShowLabel = true,
                label = { Text(text = section.title) }
            )
        }
    }
}

private enum class BottomNavigationItem(
    val icon: Int,
    val selectedIcon: Int,
    val title: String
) {
    Home(R.drawable.ic_home_inactive, R.drawable.ic_home_active, "알람 목록"),
    Alarm(R.drawable.ic_alarm_inactive, R.drawable.ic_alarm_active, "알람 보내기"),
    Account(R.drawable.ic_account_inactive, R.drawable.ic_account_active, "계정 관리"),
}

@Composable
private fun Content(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {

}