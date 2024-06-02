package com.example.lifecycleaware

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class ObserverLifecycleEvent : LifecycleEventObserver {
    private val tag = "Check Observer cycle"
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event){
            Lifecycle.Event.ON_CREATE -> Log.i(tag, "onCreate Observer 2")
            Lifecycle.Event.ON_START ->         Log.i(tag, "onStart Observer 2")
            Lifecycle.Event.ON_RESUME ->         Log.i(tag, "onResume Observer 2")
            Lifecycle.Event.ON_PAUSE ->         Log.i(tag, "onPause Observer 2")
            Lifecycle.Event.ON_STOP ->         Log.i(tag, "onStop Observer 2")
            Lifecycle.Event.ON_DESTROY ->         Log.i(tag, "onDestroy Observer 2")
            Lifecycle.Event.ON_ANY ->         Log.i(tag, "onAny Observer 2")
        }
    }
}