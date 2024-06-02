package com.example.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.ble.ble.*
import com.example.ble.databinding.ActivityMainBinding


// Activity to connect with bluetooth devices.  Based on user input, this activity communicates with a Service
// called BleService, which interacts with the BLE device via the BLE API.
class MainActivity : AppCompatActivity() {
    private var bluetoothLeService: BleService? = null
    private var bleScanner: BluetoothLeScanner? = null
    private var device: BluetoothDevice? = null
    private var isScanning = false
    private var isConnected = false
    private var deviceAddress = ""
    private val handler = Handler()
    lateinit var  binding : ActivityMainBinding

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000

    val bleApplication: BleApplication by lazy {
        applicationContext as BleApplication
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()


/**       To check crashes using crashlytics

//        val crashButton = Button(this)
//        crashButton.text = "Test Crash"
//        crashButton.setOnClickListener {
//            throw RuntimeException("Test Crash") // Force a crash
//        }
//
//        addContentView(crashButton, ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT))

*/
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomView.setupWithNavController(navController)
    }


    private fun displayGattServices(gattServices: List<BluetoothGattService>?) {
        if (gattServices == null) return
        var uuid: String?
        val unknownServiceString: String = "Unknown Service"
        val unknownCharaString: String = "Unknown characteristics"
        val gattServiceData: MutableList<HashMap<String, String>> = mutableListOf()
        val gattCharacteristicData: MutableList<ArrayList<HashMap<String, String>>> =
            mutableListOf()
//        mGattCharacteristics = mutableListOf()

        // Loops through available GATT Services.
        gattServices.forEach { gattService ->
            val currentServiceData = HashMap<String, String>()
            uuid = gattService.uuid.toString()
            Log.d("GattService", uuid.toString())
//            currentServiceData[LIST_NAME] = SampleGattAttributes.lookup(uuid, unknownServiceString)
//            currentServiceData[LIST_UUID] = uuid
//            gattServiceData += currentServiceData

            val gattCharacteristicGroupData: ArrayList<HashMap<String, String>> = arrayListOf()
            val gattCharacteristics = gattService.characteristics
            val charas: MutableList<BluetoothGattCharacteristic> = mutableListOf()

            // Loops through available Characteristics.
            gattCharacteristics.forEach { gattCharacteristic ->
                charas += gattCharacteristic
                val currentCharaData: HashMap<String, String> = hashMapOf()
                uuid = gattCharacteristic.uuid.toString()
                Log.d("GattService", uuid.toString())
                gattCharacteristicGroupData += currentCharaData
            }
            gattCharacteristicData += gattCharacteristicGroupData
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(gattReceiver(), makeGattUpdateIntentFilter())
        if (bleApplication.isServiceBounded) {
            val result = bluetoothLeService!!.connect(deviceAddress)
            Log.d("Main Activity", "Connect request result=$result")
        }
    }

    private fun makeGattUpdateIntentFilter(): IntentFilter? {
        return IntentFilter().apply {
            addAction(ACTION_GATT_CONNECTED)
            addAction(ACTION_GATT_DISCONNECTED)
        }
    }

//    override fun onPause() {
//        super.onPause()
//        unregisterReceiver(gattReceiver)
//    }

    @SuppressLint("MissingPermission")
    private fun scanLeDevice() {
        if (!isScanning) { // Stops scanning after a pre-defined scan period.
            handler.postDelayed({
                isScanning = false
                bleScanner?.stopScan(bleCallback())
            }, SCAN_PERIOD)
            isScanning = true
            bleScanner?.startScan(bleCallback())
        } else {
            isScanning = false
            bleScanner?.stopScan(bleCallback())
        }
    }


    // Device scan callback.
    private fun bleCallback(): ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            device = result.device
            deviceAddress = result.device.address
        }
    }

    private fun gattReceiver() = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.action) {
                ACTION_GATT_CONNECTED -> {
                    isConnected = true
                    scanLeDevice()
                }
                ACTION_GATT_DISCONNECTED -> {
                    isConnected = false
                }

                ACTION_GATT_SERVICES_DISCOVERED -> {
                    displayGattServices(bluetoothLeService?.getSupportedGattServices())
                }
            }
        }
    }

}