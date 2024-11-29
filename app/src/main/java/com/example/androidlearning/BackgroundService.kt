package com.example.androidlearning

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class BackgroundService : Service() {
    override fun onCreate() {
        super.onCreate()
        // Initialize resources if needed
        Log.d("MyBackgroundService", "Service Created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyBackgroundService", "Service Started")

        //* Background service runs on the main thread
        //* Perform the task in a background thread, if it is blocking
        Thread {
            performTask()
            stopSelf() // Stop the service once the task is complete
        }.start()

        return START_STICKY //* Ensures the service restarts if killed by the system
    }

    private fun performTask() {
        // Simulate a task
        for (i in 1..10) {
            Log.d("MyBackgroundService", "Task in progress: $i")
            Thread.sleep(1000) // Simulating delay
        }
        Log.d("MyBackgroundService", "Task Completed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyBackgroundService", "Service Destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        //* This service does not support binding, so return null
        return null
    }
}