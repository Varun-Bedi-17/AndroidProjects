package com.example.permissions

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.permissions.ui.theme.PermissionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PermissionsTheme {
                // ViewModel
                val viewModel = viewModel<MainViewModel>()
                val dialogQueue = viewModel.permissionDeniedDialogsQueue

                // Permission Launchers
                val cameraPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        viewModel.onPermissionResult(
                            permission = Manifest.permission.CAMERA,
                            isGranted = isGranted
                        )
                    }
                )

                val multiplePermissionsLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { permissions ->
                        permissions.keys.forEach { perm ->
                            viewModel.onPermissionResult(
                                permission = perm,
                                isGranted = permissions[perm] == true
                            )
                        }
                    }
                )


                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        cameraPermissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                    ) {
                        Text(text = "Request Camera permission")
                    }
                    Button(onClick = {
                        multiplePermissionsLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CALL_PHONE,
                            )
                        )
                    }) {
                        Text(text = "Request multiple permissions")
                    }
                }

                /** To show dialogs for ungranted permissions */
                dialogQueue
                    .reversed()
                    .forEach { perm ->
                        PermissionDeniedDialog(
                            permissionText = when (perm) {
                                Manifest.permission.CAMERA -> { CameraPermissionTextProvider() }
                                Manifest.permission.READ_EXTERNAL_STORAGE -> { StoragePermissionTextProvider() }
                                Manifest.permission.ACCESS_FINE_LOCATION -> { LocationPermissionTextProvider() }
                                Manifest.permission.CALL_PHONE-> { CallPermissionTextProvider() }
                                else -> {return@forEach}
                            },
                            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(perm),
                            onDismiss = viewModel::dismissDialog,
                            onOkCLick = {
                                viewModel.dismissDialog()
                                multiplePermissionsLauncher.launch(arrayOf(perm))
                            },
                            onGoToSettingsClick = { AppSettings() }
                        )
                    }
            }
        }
    }
}


fun Activity.AppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}