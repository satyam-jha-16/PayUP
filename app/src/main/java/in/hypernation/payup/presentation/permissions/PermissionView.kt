package `in`.hypernation.payup.presentation.permissions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.koin.androidx.compose.koinViewModel

@Composable
fun PermissionView(
    requestPermissions: Array<String>,
    viewModel: PermissionViewModel = koinViewModel(),
    isPermanentlyDeclined : (String) -> Boolean,
    openSettings: () -> Unit
){

    val key by remember {
        mutableStateOf("FIRST_TIME_LOGIN")
    }

    val dialogQueue = viewModel.visibleDialogQueue

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        requestPermissions.forEach {perm ->
            viewModel.onPermissionResult(
                perm,
                permissions[perm] == true
            )
        }

    }
    LaunchedEffect(key1 = "key") {
        permissionLauncher.launch(requestPermissions)
    }

    dialogQueue.reversed().forEach{permission ->
        PermissionDialog(
            permissionTextProvider = when(permission) {
                android.Manifest.permission.CALL_PHONE -> {
                    CallPermissionProvider()
                }
                android.Manifest.permission.READ_PHONE_STATE -> {
                    ReadPhonePermissionProvider()
                }
                else -> return@forEach
            },
            isPermanentlyDeclined = isPermanentlyDeclined(permission),
            onDismiss = viewModel::dismissDialogQueue,
            onOkClick = {
                viewModel.dismissDialogQueue()
                permissionLauncher.launch(
                    arrayOf(permission)
                )

            },
            onGoToAppSettingsClick = {
                openSettings()
            }
        )

    }

}

