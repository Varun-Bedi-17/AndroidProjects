package com.example.brodcastreciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneModeChanged : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return

        if (isAirplaneModeEnabled){
            Toast.makeText(context,"Airplane Mode Started", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"Airplane Mode Stopped", Toast.LENGTH_SHORT).show()
        }
    }
}