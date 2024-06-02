package com.example.permissions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDeniedDialog(
    permissionText: PermissionDialogTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkCLick: () -> Unit,
    onGoToSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    AlertDialog(onDismissRequest = { onDismiss() },
        buttons = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Divider()
                Text(text = if (isPermanentlyDeclined) stringResource(R.string.go_to_settings) else stringResource(
                    R.string.grant_permission),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToSettingsClick()
                            } else {
                                onOkCLick()
                            }
                        }
                        .padding(10.dp))
            }
        },
        title = {Text(text = stringResource(R.string.perm_req))},
        text = {Text(text = permissionText.getDescription(isPermanentlyDeclined))},
        modifier = modifier)
}

interface PermissionDialogTextProvider{
    fun getDescription(isPermanentlyDeclined: Boolean) : String
}

class CameraPermissionTextProvider : PermissionDialogTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined){
            "It seems you permanently declined camera permission." +
                    "You can go to app settings to grant it"
        }
        else{
            "This functionality needs to access camera permission" +
                    "Please give camera permission"
        }
    }
}

class StoragePermissionTextProvider : PermissionDialogTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined){
            "It seems you permanently declined storage permission." +
                    "You can go to app settings to grant it"
        }
        else{
            "This functionality needs to access storage permission" +
                    "Please give storage permission"
        }
    }
}

class LocationPermissionTextProvider : PermissionDialogTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined){
            "It seems you permanently declined location permission." +
                    "You can go to app settings to grant it"
        }
        else{
            "This functionality needs to access location permission" +
                    "Please give location permission"
        }
    }
}

class CallPermissionTextProvider : PermissionDialogTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined){
            "It seems you permanently declined call permission." +
                    "You can go to app settings to grant it"
        }
        else{
            "This functionality needs to access call permission" +
                    "Please give call permission"
        }
    }

}