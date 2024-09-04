package com.example.loanapplication.presentation.ui.activites

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.loanapplication.R
import com.example.loanapplication.domain.models.CallLogs
import com.example.loanapplication.domain.models.Contacts
import com.example.loanapplication.domain.models.Messages
import com.example.loanapplication.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // checkPermissions()
    }

    /*private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            loadContacts()
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            loadCallLogs()
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            loadMessages()
        }
    }*/

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtils.PERMISSION_REQUEST_CODE) {
            if (PermissionUtils.handlePermissionsResult(grantResults, permissions)) {
                // All permissions granted
                Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show()
            } else {
                // Some permissions denied
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}