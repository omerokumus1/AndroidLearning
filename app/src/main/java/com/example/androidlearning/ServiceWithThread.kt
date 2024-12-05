package com.example.androidlearning

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class ServiceWithThread : Service() {

    private var backgroundThread: Thread? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyBackgroundService", "Service Started")

        //* Background service runs on the main thread
        //* Perform the task in a background thread, if it is blocking
        backgroundThread = Thread {
            performTask()
            stopSelf() //* Stop the service once the task is complete
        }

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
        //* Stop the thread when Service is destroyed
        backgroundThread?.interrupt()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}