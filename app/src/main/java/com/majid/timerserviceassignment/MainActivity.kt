package com.majid.timerserviceassignment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.majid.timerserviceassignment.R.*

class MainActivity : AppCompatActivity() {
    private lateinit var countDownTimer: CountDownTimer

    lateinit var timerTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        timerTextView = findViewById<TextView>(id.timerTextView)

        // Set up the timer for 10 minutes (adjust as needed)
        val totalTime = 10 * 60 * 1000L // 10 minutes in milliseconds
        countDownTimer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
                // Handle timer finished event if needed
            }
        }

        countDownTimer.start()

        startService(Intent(this, TimerService::class.java))


    }
}