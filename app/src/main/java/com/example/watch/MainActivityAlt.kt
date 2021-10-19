package com.example.watch

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivityAlt : AppCompatActivity() {
    private lateinit var textSecondsElapsed: TextView
    private var secondsElapsed: Int = 0
    private var stop = false
    private lateinit var sharedPref: SharedPreferences

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = if (stop) "Seconds elapsed:${secondsElapsed} " else "Seconds elapsed:${secondsElapsed++} "
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        secondsElapsed = sharedPref.getInt("secondsElapsed", 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onResume() {
        secondsElapsed = sharedPref.getInt("secondsElapsed", 0)
        stop = false
        super.onResume()
    }

    override fun onPause() {
        with (sharedPref.edit()) {
            putInt("secondsElapsed", secondsElapsed)
            apply()
        }
        stop = true
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        with(sharedPref.edit()) {
            putInt("secondsElapsed", secondsElapsed)
            apply()
        }
    }
}