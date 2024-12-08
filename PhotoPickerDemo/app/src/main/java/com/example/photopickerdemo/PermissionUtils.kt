package com.example.photopickerdemo

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class PermissionUtils(private val activity: AppCompatActivity) {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var onPermissionsResult: ((Map<String, Boolean>) -> Unit)? = null

    private val sharedPreferences = activity.getSharedPreferences("PermissionPrefs", Context.MODE_PRIVATE)

    init {
        registerPermissionCallback()
    }

    private fun registerPermissionCallback() {
        permissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            onPermissionsResult?.invoke(permissions)
        }
    }

    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(permissions: Array<String>, callback: (Map<String, Boolean>) -> Unit) {
        onPermissionsResult = callback
        permissionLauncher.launch(permissions)
    }

    fun shouldShowRationale(permission: String): Boolean {
        return activity.shouldShowRequestPermissionRationale(permission)
    }

    fun isPermissionDeniedTwice(permission: String): Boolean {
        val denyCount = sharedPreferences.getInt(permission, 0)
        return !shouldShowRationale(permission) && denyCount >= 2
    }

    fun incrementDenyCount(permission: String) {
        val denyCount = sharedPreferences.getInt(permission, 0)
        sharedPreferences.edit().putInt(permission, denyCount + 1).apply()
    }

    fun showSettingsDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Permission Required")
            .setMessage("You have denied the permission permanently. Please enable it from the app settings.")
            .setPositiveButton("Open Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", activity.packageName, null)
                }
                activity.startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
