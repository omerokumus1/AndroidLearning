package com.example.androidlearning

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat


class ForegroundService : Service() {

    private val channelId = "channel_id"
    private val threadTask = Runnable {
        performTask()
        stopSelf() // Stop the service once the task is complete
    }
    private val thread = Thread(threadTask)

    override fun onCreate() {
        super.onCreate()
        Log.d("MyForegroundService", "Service Created")
        //* For target API >= 26, must create a notification channel
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyForegroundService", "Service Started")

        //* Create a persistent notification
        val notification = getNotification()

        //* Start the service in the foreground
        startForeground(1, notification)

        //* Service runs on main thread
        //* Perform a long-running task, if it is blocking

        thread.start()

        return START_STICKY
    }

    private fun getNotification() = NotificationCompat.Builder(this, channelId)
        .setContentTitle("Foreground Service")
        .setContentText("Performing a long-running task")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()

    private fun createNotificationChannel() {
        val nm = getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            channelId,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        nm.createNotificationChannel(channel)
    }

    private fun performTask() {
        for (i in 1..10) {
            Log.d("MyForegroundService", "Task in progress: $i")
            Thread.sleep(1000) // Simulating work
        }
        Log.d("MyForegroundService", "Task Completed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyForegroundService", "Service Destroyed")
    }

    //* Returns null because the service does not provide binding
    override fun onBind(intent: Intent?): IBinder? = null
}