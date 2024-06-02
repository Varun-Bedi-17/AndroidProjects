package com.example.wifidirect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wifidirect.databinding.ActivityMainBinding
import com.example.wifidirect.reciever.WiFiDirectBroadcastReceiver

const val TAG = "Wifi Direct Logs"
const val LIST_TAG = "Wifi Direct List"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val manager: WifiP2pManager? by lazy(LazyThreadSafetyMode.NONE) {
        getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?
    }
    var channel: WifiP2pManager.Channel? = null
    var receiver: BroadcastReceiver? = null
    private val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        channel = manager?.initialize(this, mainLooper, null)
        channel?.also {channel ->
            receiver = manager?.let { manager ->
                WiFiDirectBroadcastReceiver(manager, channel, this)
            }
        }

        binding.wifiDirectBtn.setOnClickListener {

            /* It is used to detect available peers that are in range and available for connection.
            If the discovery process succeeds and detects peers, the system broadcasts the WIFI_P2P_PEERS_CHANGED_ACTION intent,
            which you can listen for in a broadcast receiver to get a list of peers.
             */
            manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener{
                override fun onSuccess() {
                    Log.d(TAG, "Discovering successful")
                }

                override fun onFailure(reasonCode: Int) {
                    Log.d(TAG, "Discovering Unsuccessful with reason code : ${reasonCode}")
                }

            }
            )
        }

    }

    override fun onResume() {
        super.onResume()
        receiver.also {
            registerReceiver(it, intentFilter)
        }
    }

    override fun onPause() {
        super.onPause()
        receiver?.also { receiver ->
            unregisterReceiver(receiver)
        }
    }
}