package com.majid.timerserviceassignment

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.majid.timerserviceassignment.MainActivity
import com.majid.timerserviceassignment.R

class TimerService : Service() {
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var notificationManager: NotificationManager
    private val channelId = "TimerChannel"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create a notification channel
        createNotificationChannel()

        // Create a notification
        val notification = createNotification()

        // Start service in foreground
        startForeground(ONGOING_NOTIFICATION_ID, notification)

        // Set up the timer for 10 minutes (adjust as needed)
        val totalTime = 10 * 60 * 1000L // 10 minutes in milliseconds
        countDownTimer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update notification text with current time
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val notificationText = String.format("%02d:%02d", minutes, seconds)
                updateNotification(notificationText)
            }

            override fun onFinish() {
                updateNotification("00:00")
                // Handle timer finished event if needed
            }
        }

        countDownTimer.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TimerService"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Timer")
            .setContentText("Timer Text")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun updateNotification(text: String) {
//        val notification = createNotification().toBuilder()
//            .setContentText(text)
//            .build()
//
//        notificationManager.notify(ONGOING_NOTIFICATION_ID, notification)
    }

    companion object {
        private const val ONGOING_NOTIFICATION_ID = 1
    }
}
