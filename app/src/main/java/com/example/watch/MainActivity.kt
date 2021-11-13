package com.example.watch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var textSecondsElapsed: TextView
    private var secondsElapsed: Int = 0
    private var stop = false

    var backgroundThread = Thread {
        while (true) {
            if (!stop) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.seconds_elapsed, secondsElapsed++)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                secondsElapsed = getInt("seconds_elapsed")
            }
        } else {
            secondsElapsed = 0
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds_elapsed", secondsElapsed)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        stop = true
        super.onStart()
    }

    override fun onStop() {
        stop = false
        super.onStop()
    }
}