package com.example.androidlearning

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ServiceWithCoroutine : Service() {

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            performTask()
        }
        return START_STICKY
    }

    private suspend fun performTask() {
        for (i in 1..10) {
            delay(1000) // Suspend the coroutine instead of blocking
            Log.d("MyService", "Task progress: $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //* Cancel all coroutines when the Service is destroyed
        serviceJob.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}