package com.example.androidlearning

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import android.widget.Toast


//* Command to the service to display a message.
const val MSG_SAY_HELLO = 1
const val MSG_FROM_CLIENT = 2
const val MSG_FROM_SERVICE = 3

class MessengerService : Service() {
    //* Target we publish for clients to send messages to IncomingHandler.
    private lateinit var serviceMessenger: Messenger

    //* Handler to process incoming messages from the client
//    private val handler = Handler(Looper.getMainLooper()) { msg ->
//        when (msg.what) {
//            MSG_SAY_HELLO ->
//                Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT).show()
//
//            MSG_FROM_CLIENT -> {
//                val clientMessenger = msg.replyTo // Get the client's Messenger
//                val responseMessage =
//                    Message.obtain(null, MSG_FROM_SERVICE, "Hello from Service!")
//                clientMessenger?.send(responseMessage) // Send a response back
//
//            }
//        }
//        true
//    }


    //* Handler of incoming messages from clients.
    class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler(applicationContext.mainLooper) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_SAY_HELLO ->
                    Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT).show()

                MSG_FROM_CLIENT -> {
                    val clientMessenger = msg.replyTo // Get the client's Messenger
                    val responseMessage =
                        Message.obtain(null, MSG_FROM_SERVICE, "Hello from Service!")
                    clientMessenger?.send(responseMessage) // Send a response back

                }

                else -> super.handleMessage(msg)
            }
        }
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
        Log.d("MessengerService", "Service Destroyed")
    }

    override fun onBind(intent: Intent?): IBinder {
        Toast.makeText(applicationContext, "binding", Toast.LENGTH_SHORT).show()
        serviceMessenger = Messenger(IncomingHandler(this))
        return serviceMessenger.binder
    }
}