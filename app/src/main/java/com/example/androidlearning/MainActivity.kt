package com.example.androidlearning

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var boundService: BoundService
    private var isBound = false
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("MainActivity", "Service Connected")
            boundService = (service as BoundService.MyBinder).getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("MainActivity", "Service Disconnected")
            isBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Intent(this, BoundService::class.java).also {
            bindService(it, serviceConnection, BIND_AUTO_CREATE)
        }

        binding.button.setOnClickListener {
            if (isBound) {
                boundService.performTask()
            }
        }

        binding.unbindBtn.setOnClickListener {
            if (isBound) {
                unbindService(serviceConnection)
                isBound = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(serviceConnection)
        }
    }


}
