package com.example.androidlearning

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.util.Log


class ServiceWithHandlerThread : Service() {

    private lateinit var handlerThread: HandlerThread
    private lateinit var serviceHandler: Handler

    override fun onCreate() {
        super.onCreate()
        handlerThread = HandlerThread("ServiceHandlerThread")
        handlerThread.start()
        serviceHandler = Handler(handlerThread.looper)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceHandler.post {
            performTask()
        }
        return START_STICKY //* Ensures the service restarts if killed by the system
    }

    private fun performTask() {
        for (i in 1..10) {
            Log.d("MyBackgroundService", "Task in progress: $i")
            Thread.sleep(1000) // Simulating delay
        }
        Log.d("MyBackgroundService", "Task Completed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyBackgroundService", "Service Destroyed")
        handlerThread.quitSafely() // Clean up the HandlerThread
    }

    override fun onBind(intent: Intent?): IBinder? = null
}