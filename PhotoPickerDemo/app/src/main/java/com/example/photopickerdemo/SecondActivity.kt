package com.example.photopickerdemo

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView

class SecondActivity : AppCompatActivity(), MediaBottomSheetFragment.ManageCallback {
    private lateinit var image: AppCompatImageView
    private lateinit var permissionUtils: PermissionUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        permissionUtils = PermissionUtils(this)
        image = findViewById(R.id.img_selected)

        this.findViewById<AppCompatButton>(R.id.media_select).setOnClickListener {
            handleMediaSelection(MediaType.PHOTO_AND_VIDEO)
        }

        this.findViewById<AppCompatButton>(R.id.photo_select).setOnClickListener {
            handleMediaSelection(MediaType.PHOTO)
        }

        this.findViewById<AppCompatButton>(R.id.video_select).setOnClickListener {
            handleMediaSelection(MediaType.VIDEO)
        }
    }

    private fun handleMediaSelection(mediaType: MediaType) {
        val permissions = getPermissionsForMediaType(mediaType)
        checkAndRequestPermission(permissions, mediaType)
    }

    private fun getPermissionsForMediaType(mediaType: MediaType): Array<String> {
        return when (mediaType) {
            MediaType.PHOTO_AND_VIDEO -> when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> arrayOf(
                    READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, READ_MEDIA_VISUAL_USER_SELECTED
                )

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arrayOf(
                    READ_MEDIA_IMAGES, READ_MEDIA_VIDEO
                )

                else -> arrayOf(READ_EXTERNAL_STORAGE)
            }

            MediaType.PHOTO -> when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> arrayOf(
                    READ_MEDIA_IMAGES, READ_MEDIA_VISUAL_USER_SELECTED
                )

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arrayOf(
                    READ_MEDIA_IMAGES
                )

                else -> arrayOf(READ_EXTERNAL_STORAGE)
            }

            MediaType.VIDEO -> when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> arrayOf(
                    READ_MEDIA_VIDEO, READ_MEDIA_VISUAL_USER_SELECTED
                )

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arrayOf(
                    READ_MEDIA_VIDEO
                )

                else -> arrayOf(READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkAndRequestPermission(permissions: Array<String>, mediaType: MediaType) {
        // Check if at least one required permission is granted for Android 14
        val isAnyPermissionGranted = permissions.any { permissionUtils.isPermissionGranted(it) }

        // If any permission is granted, proceed
        if (isAnyPermissionGranted) {
            onPermissionGranted(mediaType)
            return
        }

        requestPermission(permissions, mediaType)
    }

    private fun requestPermission(permissions: Array<String>, mediaType: MediaType) {
        permissionUtils.requestPermissions(permissions) { result ->
            val deniedPermissions = result.filterValues { !it }.keys

            // If all permissions are granted after the request
            if (deniedPermissions.isEmpty() || permissions.any {
                    permissionUtils.isPermissionGranted(it)
                }) {
                onPermissionGranted(mediaType)
            } else {
                // Check if the settings dialog should be shown
                val shouldShowDialog =
                    deniedPermissions.any { permissionUtils.isPermissionDeniedTwice(it) }

                if (shouldShowDialog) {
                    permissionUtils.showSettingsDialog()
                } else {
                    deniedPermissions.forEach { permission ->
                        permissionUtils.incrementDenyCount(permission)
                        // Optionally, show rationale or other feedback
                    }
                }
            }
        }
    }

    private fun onPermissionGranted(mediaType: MediaType) {
        val bottomSheet = MediaBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putParcelable("MEDIA_TYPE", mediaType)
            }
        }
        bottomSheet.show(supportFragmentManager, "MediaBottomSheet")
    }

    override fun onManageCallback(mediaType: MediaType) {
        val permissions = getPermissionsForMediaType(mediaType)
        requestPermission(permissions, mediaType)
    }

}