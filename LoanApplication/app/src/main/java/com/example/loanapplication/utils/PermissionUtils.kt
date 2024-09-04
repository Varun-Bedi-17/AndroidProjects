package com.example.loanapplication.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {

    const val PERMISSION_REQUEST_CODE = 100

    // List of permissions required
    private val requiredPermissions = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_SMS
    )

    /**
     * Checks if all required permissions are granted.
     * @param activity The activity context to check permissions.
     * @return True if all permissions are granted, false otherwise.
     */
    fun arePermissionsGranted(activity: Activity): Boolean {
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * Requests the necessary permissions if they are not granted.
     * @param activity The activity context to request permissions.
     */
    fun requestPermissions(activity: Activity) {
        val permissionsNeeded = mutableListOf<String>()

        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsNeeded.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

    /**
     * Handles the result of the permission request.
     * @param grantResults The grant results for the corresponding permissions.
     * @param permissions The requested permissions.
     * @return True if all permissions were granted, false otherwise.
     */
    fun handlePermissionsResult(grantResults: IntArray, permissions: Array<out String>): Boolean {
        for ((_, result) in grantResults.withIndex()) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}
