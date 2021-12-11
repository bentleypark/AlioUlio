package com.alio.ulio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.alio.ulio.ui.MainScreen
import com.alio.ulio.ui.theme.AlioUlioTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlioUlioTheme {
                RequiresRecordVoicePermission()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlioUlioTheme {
        MainScreen()
    }
}

@ExperimentalPermissionsApi
@Composable
private fun RequiresRecordVoicePermission(
//    navigateToSettingsScreen: () -> Unit
) {
    // Track if the user doesn't want to see the rationale any more.
    var doNotShowRationale = remember { mutableStateOf(false) }

    // Camera permission state
    val recordVoicePermissionState = rememberPermissionState(
        android.Manifest.permission.RECORD_AUDIO
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    recordVoicePermissionState.launchPermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    when {
        // If the camera permission is granted, then show screen with the feature enabled
        recordVoicePermissionState.hasPermission -> {
            MainScreen()
        }
        // If the user denied the permission but a rationale should be shown, or the user sees
        // the permission for the first time, explain why the feature is needed by the app and allow
        // the user to be presented with the permission again or to not see the rationale any more.
        recordVoicePermissionState.shouldShowRationale ||
                !recordVoicePermissionState.permissionRequested -> {
            if (doNotShowRationale.value) {
                Text("Feature not available")
            } else {
//                Column {
//                    Text("The camera is important for this app. Please grant the permission.")
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Row {
//                        Button(onClick = { recordVoicePermissionState.launchPermissionRequest() }) {
//                            Text("Request permission")
//                        }
//                        Spacer(Modifier.width(8.dp))
//                        Button(onClick = { doNotShowRationale.value = true }) {
//                            Text("Don't show rationale again")
//                        }
//                    }
//                }
            }
        }
        // If the criteria above hasn't been met, the user denied the permission. Let's present
        // the user with a FAQ in case they want to know more and send them to the Settings screen
        // to enable it the future there if they want to.
        else -> {
//            Column {
//                Text(
//                    "Camera permission denied. See this FAQ with information about why we " +
//                            "need this permission. Please, grant us access on the Settings screen."
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Button(onClick = navigateToSettingsScreen) {
//                    Text("Open Settings")
//                }
//            }
        }
    }
}