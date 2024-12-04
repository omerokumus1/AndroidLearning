package com.example.androidlearning

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //* Handler to process messages from the Service
    private val clientHandler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            MSG_FROM_SERVICE -> {
                val reply = msg.obj as String
                Toast.makeText(this, reply, Toast.LENGTH_SHORT).show()
            }
        }
        true
    }

    //* Messenger for the client to receive responses
    private val clientMessenger = Messenger(clientHandler)


    //* Messenger for communicating with the service.
    private var serviceMessenger: Messenger? = null

    //* Flag indicating whether we have called bind on the service.
    private var isBound = false

    //* ServiceConnection to manage binding to the Service
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            //* This is called when the connection with the service has been
            //* established, giving us the object we can use to
            //* interact with the service.  We are communicating with the
            //* service using a Messenger, so here we get a client-side
            //* representation of that from the raw IBinder object.
            Log.d("MainActivity", "Service Connected")
            serviceMessenger = Messenger(service)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            //* This is called when the connection with the service has been
            //* unexpectedly disconnected&mdash;that is, its process crashed.
            Log.d("MainActivity", "Service Disconnected")
            serviceMessenger = null
            isBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Intent(this, MessengerService::class.java).also {
            bindService(it, serviceConnection, BIND_AUTO_CREATE)
        }

        binding.button.setOnClickListener {
            if (isBound) {
                try {
                    val msg: Message = Message.obtain(null, MSG_FROM_CLIENT)
                    msg.replyTo = clientMessenger
                    serviceMessenger?.send(msg)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
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
