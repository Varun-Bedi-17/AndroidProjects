package com.example.pip

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.Display
import android.view.View
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.transition.Visibility
import com.example.pip.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val d: Display = windowManager.defaultDisplay
        val p = Point()
        d.getSize(p)
        val width: Int = p.x
        val height: Int = p.y

        val ratio = Rational(width, height)
        val pipBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PictureInPictureParams.Builder()
                .setAspectRatio(ratio)
                .setAutoEnterEnabled(true)
                .setSeamlessResizeEnabled(false)                          // Provides much smoother animation.
                .build()
        } else {
            PictureInPictureParams.Builder()
                .setAspectRatio(ratio)
                .build()
        }

        setPictureInPictureParams(pipBuilder)

        binding.enterPip.setOnClickListener {
            enterPictureInPictureMode(pipBuilder)
        }
        
        setContentView(binding.root)
    }


    /** Compiler doesn't wait for onUserLeave Callback if we set setAutoEnterEnabled to true.
     * therefore, it will call only above api level 12
     */
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        val ratio = Rational(1000, 2000)
        val pipBuilder =
            PictureInPictureParams.Builder()
                .setAspectRatio(ratio)
                .build()
        enterPictureInPictureMode(pipBuilder)
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean,
                                               newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (isInPictureInPictureMode) {
            // Hide the full-screen UI (controls, etc.) while in PiP mode.
            binding.text.visibility = View.GONE
        } else {
            // Restore the full-screen UI.
            binding.text.visibility = View.VISIBLE
        }
    }

    override fun onTopResumedActivityChanged(topResumed: Boolean) {
        Log.d("PIP", "bfdkjnjkc kjnkjb")
        if (topResumed) {
            // Top resumed activity
            // Can be a signal to re-acquire exclusive resources
            Log.d("PIP", "bfdkjnjkc kjnkjbgyugu")
        } else {
            // No longer the top resumed activity
            Log.d("PIP", "bfdkjnjkc kjnkjb")
        }
    }
}