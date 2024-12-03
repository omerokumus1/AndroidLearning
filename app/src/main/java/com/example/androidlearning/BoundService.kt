package com.example.androidlearning

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log


class BoundService : Service() {

    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService(): BoundService {
            return this@BoundService
        }

        //* Other client functions can be added here
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize resources if needed
        Log.d("MyBackgroundService", "Service Created")
    }

    //* This function is for clients
    fun performTask() {
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

    override fun onBind(intent: Intent?): IBinder {
        return binder
        // or
//        return MyBinder()
    }
}