package com.example.lifecycleaware

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner


class ActivityObserver : DefaultLifecycleObserver {

    private val tag = "Check Observer cycle"
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.i(tag, "onCreate Observer")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.i(tag, "onStart Observer")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.i(tag, "onResume Observer")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.i(tag, "onPause Observer")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.i(tag, "onStop Observer")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.i(tag, "onDestroy Observer")

    }

}
