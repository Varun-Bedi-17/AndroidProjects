package com.example.wifi

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.wifi.ui.theme.WifiTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val wifiManager = context.getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager

            val wifiScanReceiver = object : BroadcastReceiver(){
                override fun onReceive(context: Context?, intent: Intent?) {
                    val success = intent?.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                    if(success == true){
                        scanSuccess(wifiManager)
                    }
                    else{
                        scanFailure(wifiManager)
                    }
                }
            }
            val intentFilter = IntentFilter()
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
            context.registerReceiver(wifiScanReceiver, intentFilter)
            val success = wifiManager.startScan()
            if(!success){
                scanFailure(wifiManager)
            }


            Surface(modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background) {
                Greeting("Android")
            }
        }


    }

    private fun scanSuccess(wifiManager: WifiManager) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Scan Results", "No Permission")
        }
        else{
            val results =  wifiManager.scanResults
            results.forEach {
                Log.d("Wifi List", it.SSID)
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun scanFailure(wifiManager: WifiManager) {
        val results =  wifiManager.scanResults
       Log.d("Scan Results", results.toString())
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WifiTheme {
        Greeting("Android")
    }
}