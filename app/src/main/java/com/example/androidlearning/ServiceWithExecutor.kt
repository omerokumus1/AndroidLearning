package com.example.androidlearning

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.Executors

class ServiceWithExecutor : Service() {
    private val executor = Executors.newFixedThreadPool(3)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        executor.execute {
            performTask()
        }
        return START_STICKY
    }

    private fun performTask() {
        for (i in 1..10) {
            Thread.sleep(1000) // Simulate a long-running task
            Log.d("MyService", "Task progress: $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdownNow() // Shut down the executor
    }

    override fun onBind(intent: Intent?): IBinder? = null
}