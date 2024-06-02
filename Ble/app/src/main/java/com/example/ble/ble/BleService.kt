package com.example.ble.ble

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.bluetooth.*
import android.content.Intent
import android.content.pm.PackageManager
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.*

private const val TAG = "BleService"
const val ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED"
const val ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED"
const val ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED"
const val ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE"
val UUID_HEART_RATE_MEASUREMENT = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT)
private const val STATE_DISCONNECTED = 0
private const val STATE_CONNECTED = 2

class BleService : Service() {
    private val binder = LocalBinder()
    var bluetoothAdapter : BluetoothAdapter? = null
    private var connectionState = STATE_DISCONNECTED
    var bluetoothGatt: BluetoothGatt? = null


    override fun onBind(intent: Intent): IBinder {
        return binder
    }


    /** To check whether ble adapter is available on device or not. Once service is bound to, it needs to access the ble adapter.*/
    fun initialize(): Boolean {
        val bluetoothManager : BluetoothManager? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSystemService(BluetoothManager::class.java)
        } else {
            null
        }
        bluetoothAdapter = bluetoothManager?.adapter
        if (bluetoothAdapter == null){
            Log.d("Not found", "Unable to obtain a BluetoothAdapter.")
            return false
        }
        return true
    }



/**    Once the BluetoothService is initialized, it can connect to the BLE device.
    The activity needs to send the device address to the service so it can initiate the connection.
    The service will first call getRemoteDevice() on the BluetoothAdapter to access the device.
    If the adapter is unable to find a device with that address, getRemoteDevice() throws an IllegalArgumentException.*/
    fun connect(address : String): Boolean {

    bluetoothAdapter?.let {
        try {
            val device = it.getRemoteDevice(address)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // to check permissions
            }
            else {
                bluetoothGatt = device.connectGatt(this, false, bluetoothGattCallback)
            }
        } catch (e: IllegalArgumentException) {
            Log.d(TAG, "Device not found with provided address.")
            return false
        } catch (e: Exception) {
            Log.d(TAG, "Exception occured: ${e.localizedMessage}")
            return false
        } ?: run {
            Log.d(TAG, "BluetoothAdapter not initialized")
            return false
        }
    }
    return false
    }

    // Connection to gatt requires a BluetoothGattCallback to receive notifications about the connection state, service discovery,
    // characteristic reads, and characteristic notifications.
    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // successfully connected to the GATT Server
                connectionState = STATE_CONNECTED
                broadCastUpdates(ACTION_GATT_CONNECTED)

                // Attempts to discover services after successful connection.
                gatt?.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                // disconnected from the GATT Server
                connectionState = STATE_DISCONNECTED
                broadCastUpdates(ACTION_GATT_DISCONNECTED)
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadCastUpdates(ACTION_GATT_SERVICES_DISCOVERED)
            } else {
                Log.w(TAG, "onServicesDiscovered received: $status")
            }
        }
        override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int)
        {
            if (status == BluetoothGatt.GATT_SUCCESS) {
//                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic)
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic) {
//            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic)
        }
    }


    /** To notify whether server is connected to gatt or not */
    private fun broadCastUpdates(action : String){
        val intent = Intent(action)
        sendBroadcast(intent)
    }

    private fun broadcastUpdate(action: String, characteristic: BluetoothGattCharacteristic) {
        val intent = Intent(action)

        // This is special handling for the Heart Rate Measurement profile. Data
        // parsing is carried out as per profile specifications.
        when (characteristic.uuid) {
            UUID_HEART_RATE_MEASUREMENT -> {
                val flag = characteristic.properties
                val format = when (flag and 0x01) {
                    0x01 -> {
                        Log.d(TAG, "Heart rate format UINT16.")
                        BluetoothGattCharacteristic.FORMAT_UINT16
                    }
                    else -> {
                        Log.d(TAG, "Heart rate format UINT8.")
                        BluetoothGattCharacteristic.FORMAT_UINT8
                    }
                }
                val heartRate = characteristic.getIntValue(format, 1)
                Log.d(TAG, String.format("Received heart rate: %d", heartRate))
                intent.putExtra(EXTRA_DATA, (heartRate).toString())
            }
            else -> {
                // For all other profiles, writes the data formatted in HEX.
                val data: ByteArray? = characteristic.value
                if (data?.isNotEmpty() == true) {
                    val hexString: String = data.joinToString(separator = " ") {
                        String.format("%02X", it)
                    }
                    intent.putExtra(EXTRA_DATA, "$data\n$hexString")
                }
            }
        }
        sendBroadcast(intent)
    }


    @SuppressLint("MissingPermission")
    fun setCharacteristicNotification(
        characteristic: BluetoothGattCharacteristic,
        enabled: Boolean
    ) {
        bluetoothGatt?.let { gatt ->
            gatt.setCharacteristicNotification(characteristic, enabled)

            // This is specific to Heart Rate Measurement.
            if (UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT) == characteristic.uuid) {
                val descriptor = characteristic.getDescriptor(UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG))
                descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt.writeDescriptor(descriptor)
            }
        } ?: run {
            Log.w(TAG, "BluetoothGatt not initialized")
        }
    }



    // To close the connection when we are finished with it.
    override fun onUnbind(intent: Intent?): Boolean {
        close()
        return super.onUnbind(intent)
    }

    private fun close() {
        bluetoothGatt?.let { gatt ->
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            {
                // Request permissions
            }
            else {
                gatt.close()
                bluetoothGatt = null
            }
        }
    }

    /** Once the services have been discovered, the service can call getServices() to get the reported data.*/
    fun getSupportedGattServices(): List<BluetoothGattService>? {
        return bluetoothGatt?.services
    }


    inner class LocalBinder : Binder(){
        fun getService() = this@BleService
    }



}
