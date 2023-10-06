package com.example.androidlearning

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Process
import android.widget.Toast


class BasicService : Service() {
    override fun onCreate() {

    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        // If we get killed, after returning from here, restart
        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }
    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }
}