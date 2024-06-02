package com.example.ble

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.ble.ble.BleService

class BleApplication : Application() {
    var bluetoothLeService: BleService? = null
    var isServiceBounded = false

    override fun onCreate() {
        super.onCreate()

        // Gatt is used to interact with the Ble device connected to it.
        val gattServiceIntent = Intent(this, BleService::class.java)
        bindService(gattServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    // To manage service connection.
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            bluetoothLeService = (binder as BleService.LocalBinder).getService()
            isServiceBounded = true
//            bleScanner = bluetoothLeService?.bluetoothAdapter?.bluetoothLeScanner
//            bluetoothLeService?.let {
//                if (!it.initialize()) {
//                    Log.d("Not found", "Unable to initialize bluetooth")
//                    finish()
//                }
//                // perform device connection
//                scanLeDevice()
//            }
        }
        override fun onServiceDisconnected(componentName: ComponentName?) {
            bluetoothLeService = null
            isServiceBounded =false
        }
    }
}