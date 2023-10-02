package com.example.androidlearning

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.UUID
import java.util.logging.Handler

@SuppressLint("MissingPermission")
class BluetoothConnectionService private constructor(
    var mContext: Context,
    val mBluetoothAdapter: BluetoothAdapter,
    var mHandler: android.os.Handler
) {
    private var mAcceptThread: AcceptThread? = null
    private var mConnectThread: ConnectThread? = null
    private var mBluetoothDevice: BluetoothDevice? = null
    private var deviceUUID: UUID? = null
    var mProgressDialog: ProgressDialog? = null
    private var mConnectedThread: ConnectedThread? = null

    init {
        start()
    }

    companion object {
        private const val TAG = "BluetoothConnectionServ"
        private const val appName = "MYAPP"
        val BT_UUID: UUID = UUID.fromString("00001105-0000-1000-8000-00805f9b34fb")
        val MESSAGE_READ = 100

    }

    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
    @Synchronized
    fun start() {
        Log.d(TAG, "start")

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread!!.cancel()
            mConnectThread = null
        }
        if (mAcceptThread == null) {
            mAcceptThread = AcceptThread()
            mAcceptThread!!.start()
        }
    }

    /**
     *
     * AcceptThread starts and sits waiting for a connection.
     * Then ConnectThread starts and attempts to make a connection with the other devices AcceptThread.
     */
    fun startClient(device: BluetoothDevice, uuid: UUID?) {
        Log.d(TAG, "startClient: Started.")

        //initprogress dialog
        mProgressDialog = ProgressDialog.show(
            mContext, "Connecting Bluetooth", "Please Wait...", true
        )
        mConnectThread = ConnectThread(device, uuid)
        mConnectThread!!.start()
    }

    private fun connected(mmSocket: BluetoothSocket) {
        Log.d(TAG, "connected: Starting.")

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = ConnectedThread(mmSocket)
        mConnectedThread!!.start()
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param out The bytes to write
     * @see ConnectedThread.write
     */
    fun write(out: ByteArray) {

        // Synchronize a copy of the ConnectedThread
        Log.d(TAG, "write: Write Called.")
        //perform the write
        if (mConnectedThread != null) mConnectedThread?.write(out)
        else Log.d(TAG, "write: mConnectedThread is null")

    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     * !!! We wont need it.
     */
    private inner class AcceptThread : Thread() {
        // The local server socket
        private val mBluetoothServerSocket: BluetoothServerSocket?

        init {
            var tmp: BluetoothServerSocket? = null

            // Create a new listening server socket
            try {
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(
                    appName,
                    BT_UUID
                )
                Log.d(TAG, "AcceptThread: Setting up Server using: $BT_UUID")
            } catch (e: IOException) {
                Log.e(TAG, "AcceptThread: IOException: " + e.message)
            }
            mBluetoothServerSocket = tmp
        }

        override fun run() {
            Log.d(TAG, "run: AcceptThread Running.")
            var socket: BluetoothSocket? = null
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                Log.d(TAG, "run: RFCOM server socket start.....")
                socket = mBluetoothServerSocket!!.accept()
                Log.d(TAG, "run: RFCOM server socket accepted connection.")
            } catch (e: IOException) {
                Log.e(TAG, "AcceptThread: IOException: " + e.message)
            }

            //talk about this is in the 3rd
            socket?.let { connected(it) }
            Log.i(TAG, "END mAcceptThread ")
        }

        fun cancel() {
            Log.d(TAG, "cancel: Canceling AcceptThread.")
            try {
                mBluetoothServerSocket!!.close()
            } catch (e: IOException) {
                Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.message)
            }
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private inner class ConnectThread(device: BluetoothDevice, uuid: UUID?) : Thread() {
        //        private var mBluetoothSocket: BluetoothSocket? = null
        private val mBluetoothSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            Log.d(
                TAG, "ConnectThread: Trying to create RfcommSocket using UUID: "
                        + BT_UUID
            )
            Log.d(TAG, "device: ${device.name}, ${device.address}")
            val btSocket: BluetoothSocket
            try {
                btSocket =  device.createRfcommSocketToServiceRecord(uuid)
                Log.d(TAG, "ConnectThread: btSocket is created: $btSocket")
                return@lazy btSocket
            } catch (e: IOException) {
                Log.d(TAG, "exception on device.createRfcommSocketToServiceRecord(uuid)")
                return@lazy null
            }


        }

        init {
            Log.d(TAG, "ConnectThread: started.")
            mBluetoothDevice = device
            deviceUUID = uuid
        }

        override fun run() {
            Log.i(TAG, "RUN mConnectThread ")

            // Cancel discovery because it otherwise slows down the connection.
            mBluetoothAdapter.cancelDiscovery()

            Log.d(
                TAG,
                "ConnectThread: connect() will be called on mBluetoothSocket if mBluetoothSocket is not null. mBluetoothSocket: $mBluetoothSocket"
            )
            mBluetoothSocket?.let { socket ->
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                Log.d(TAG, "ConnectThread: connect() is called on mBluetoothSocket")
                try {
                    socket.connect()
                    Log.d(
                        TAG,
                        "ConnectThread: connected through mBluetoothSocket: $mBluetoothSocket"
                    )
                    // The connection attempt succeeded. Perform work associated with
                    // the connection in a separate thread.
                    connected(socket)
                } catch (e: IOException) {
                    Log.d(TAG, "ConnectThread: connection failure. error: ${e.message} ")
                    socket.close()
                }
            }
        }

        fun cancel() {
            try {
                Log.d(TAG, "cancel: Closing Client Socket.")
                mBluetoothSocket!!.close()
            } catch (e: IOException) {
                Log.e(TAG, "cancel: close() of mmSocket in Connectthread failed. " + e.message)
            }
        }
    }

    /**
     * Finally the ConnectedThread which is responsible for maintaining the BTConnection, Sending the data, and
     * receiving incoming data through input/output streams respectively.
     */
    private inner class ConnectedThread(socket: BluetoothSocket) : Thread() {
        private val mBluetoothSocket: BluetoothSocket
        private val mmInStream: InputStream
        private val mmOutStream: OutputStream

        init {
            Log.d(TAG, "ConnectedThread: Starting.")
            mBluetoothSocket = socket
            mmInStream = mBluetoothSocket.inputStream
            mmOutStream = mBluetoothSocket.outputStream
            mProgressDialog?.cancel()
        }

        override fun run() {
            val buffer = ByteArray(2048) // buffer store for the stream
            var numBytes: Int // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                // Read from the InputStream
                numBytes = try {
                    mmInStream.read(buffer)
                } catch (e: IOException) {
                    Log.d(TAG, "Input stream was disconnected: " + e.message)
                    break
                }

                val incomingMessage = String(buffer, 0, numBytes)
                Log.d(
                    TAG,
                    "InputStream: $incomingMessage"
                )

                // Send the obtained bytes to the UI activity.
//                val readMsg = android.os.Handler().obtainMessage(
//                    MESSAGE_READ, numBytes, -1,
//                    mmBuffer)
                val readMsg = mHandler.obtainMessage(MESSAGE_READ, incomingMessage)
                readMsg.sendToTarget()
            }
        }

        //Call this from the main activity to send data to the remote device
        fun write(bytes: ByteArray) {
            val text = String(bytes, Charset.defaultCharset())
            Log.d(
                TAG,
                "write: Writing to outputstream: $text"
            )
            try {
                mmOutStream.write(bytes)
            } catch (e: IOException) {
                Log.e(TAG, "write: Error writing to output stream. " + e.message)
            }
        }

        /* Call this from the main activity to shutdown the connection */
        fun cancel() {
            try {
                mBluetoothSocket.close()
            } catch (e: IOException) {
            }
        }
    }


}