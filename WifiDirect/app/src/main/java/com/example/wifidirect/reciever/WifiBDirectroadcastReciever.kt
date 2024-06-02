package com.example.wifidirect.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import com.example.wifidirect.LIST_TAG
import com.example.wifidirect.MainActivity
import com.example.wifidirect.TAG

/**
 * A BroadcastReceiver that notifies of important Wi-Fi p2p events.
 */
class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: MainActivity
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // Check to see if Wi-Fi is enabled and notify appropriate activity
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                when (state) {
                    WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {
                        Log.d(TAG, "Wifi is enabled")
                    }
                    else -> {
                        Log.d(TAG, "Wifi is not enabled")
                    }
                }
            }

            // Callback of discoverPeers() success listener.
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                Log.d(TAG, "Peer change action")
                // Call WifiP2pManager.requestPeers() to get a list of current peers
                manager.requestPeers(channel){ peersList ->
                    peersList.deviceList.forEach {
                        Log.d(LIST_TAG, it.deviceName)
                        // After getting list of network, we need to call the connect() method according on which we want to connect with.

                        val device: WifiP2pDevice = it
                        val config = WifiP2pConfig()
                        config.deviceAddress = device.deviceAddress
                        channel?.also { channel ->
                            manager?.connect(channel, config, object : WifiP2pManager.ActionListener {

                                override fun onSuccess() {
                                    //success logic
                                    Log.d(TAG, "Device connected successfully")
                                }

                                override fun onFailure(reason: Int) {
                                    //failure logic
                                    Log.d(TAG, "Device not connected")
                                }
                            }
                            )}
                    }
                }


            }

            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                // Respond to new connection or disconnections
            }

            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                // Respond to this device's wifi state changing
            }
        }
    }
}