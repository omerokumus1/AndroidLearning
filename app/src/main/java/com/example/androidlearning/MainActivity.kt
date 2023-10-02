package com.example.androidlearning

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androidlearning.databinding.ActivityMainBinding
import java.nio.charset.Charset
import java.util.UUID


// https://stackoverflow.com/questions/67722950/android-12-new-bluetooth-permissions
@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_ENABLE_BT = 1
        const val LOG_TAG = "and-bluetooth"
        val MY_UUID_INSECURE = BluetoothConnectionService.BT_UUID
    }

    /*
    * Request says: Determine relative positions of nearby devices. Look it up
    * */

    private lateinit var binding: ActivityMainBinding
    private lateinit var handler: Handler

    private lateinit var bluetoothManager: BluetoothManager
    private var bluetoothAdapter: BluetoothAdapter? = null

    private var mBTDevice: BluetoothDevice? = null
    private var mBluetoothConnection: BluetoothConnectionService? = null

    private val bluetoothPermissionsLegacy = arrayOf(
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN,
    )

    @RequiresApi(Build.VERSION_CODES.S)
    private val bluetoothPermissionsApi31 = arrayOf(
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADVERTISE
    )

    val btDevices = mutableListOf<BluetoothDevice>()
    var deviceListAdapter: DeviceListAdapter? = null


    // Bluetooth'un açılıp açılmaması için gelen dialog ekranında verilen cevap
    private var bluetoothActivityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Log.i(LOG_TAG, "onActivityResult -> Allow clicked")
            } else {
                Log.i(LOG_TAG, "onActivityResult -> Deny clicked")
            }
        }

    // Bluetooth request for API < 31
    private var requestBluetooth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Bluetooth'un açılıp açılmaması için gelen dialog ekranında verilen cevap
            // RESULT_OK = Allow
            // RESULT_CANCELED = Deny
            if (result.resultCode == RESULT_OK) {
                Log.i(LOG_TAG, "requestBluetooth -> RESULT_OK (Allow clicked)")
                bluetoothPermissionsLegacy.forEach {
                    Log.i(LOG_TAG, "$it ->  ${isPermissionGranted(it)}")
                }
            } else if (result.resultCode == RESULT_CANCELED) {
                Log.i(LOG_TAG, "requestBluetooth -> RESULT_CANCELED (Deny clicked)")
            }
        }

    // Bluetooth request for API >= 31
    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d(LOG_TAG, "${it.key} = ${it.value}")
            }
            if (areBluetoothPermissionsGranted()) {
                enableBluetooth()
            } else {
                Log.d(LOG_TAG, "registerForActivityResult -> Bluetooth permission denied")
            }
        }

    private val broadcastReceiverForBtState = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                    BluetoothAdapter.STATE_OFF -> Log.i(LOG_TAG, "onReceive: STATE OFF")
                    BluetoothAdapter.STATE_TURNING_OFF -> Log.i(
                        LOG_TAG,
                        "onReceive: STATE TURNING OFF"
                    )

                    BluetoothAdapter.STATE_ON -> Log.i(LOG_TAG, "onReceive: STATE ON")
                    BluetoothAdapter.STATE_TURNING_ON -> Log.i(
                        LOG_TAG,
                        "onReceive: STATE TURNING OFF"
                    )
                }
            }
        }
    }

    private val broadcastReceiverForBtDiscoverability = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (action == BluetoothAdapter.ACTION_SCAN_MODE_CHANGED) {
                when (intent.getIntExtra(
                    BluetoothAdapter.EXTRA_SCAN_MODE,
                    BluetoothAdapter.ERROR
                )) {
                    BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE -> Log.i(
                        LOG_TAG,
                        "SCAN_MODE_CONNECTABLE_DISCOVERABLE: Discoverability enabled"
                    )

                    BluetoothAdapter.SCAN_MODE_CONNECTABLE -> Log.i(
                        LOG_TAG,
                        "SCAN_MODE_CONNECTABLE: Discoverability disabled. Able to receive connections from devices which previously discovered this device."
                    )

                    BluetoothAdapter.SCAN_MODE_NONE -> Log.i(
                        LOG_TAG,
                        "SCAN_MODE_NONE: Discoverability disabled. Not able to receive connections"
                    )

                    BluetoothAdapter.STATE_CONNECTING -> Log.i(
                        LOG_TAG,
                        "STATE_CONNECTING: Connecting"
                    )

                    BluetoothAdapter.STATE_CONNECTED -> Log.i(LOG_TAG, "STATE_CONNECTED: Connected")
                }
            }

        }
    }

    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private val broadcastReceiverForDiscoveringDevices: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                Log.d(LOG_TAG, "onReceive: ACTION FOUND.")
                if (action == BluetoothDevice.ACTION_FOUND) {
                    val device =
                        intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {
                        if (device !in btDevices) btDevices.add(device)

                        Log.d(LOG_TAG, "onReceive: " + device.name + ": " + device.address)
                        deviceListAdapter =
                            DeviceListAdapter(context, R.layout.device_adapter_view, btDevices)
                        binding.deviceList.adapter = deviceListAdapter
                    }

                }
            }
        }

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    private val broadcastReceiverForBonding: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == BluetoothDevice.ACTION_BOND_STATE_CHANGED) {
                val mDevice =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                //3 cases:
                //case1: bonded already
                if (mDevice?.bondState == BluetoothDevice.BOND_BONDED) {
                    Log.d(LOG_TAG, "BroadcastReceiver: BOND_BONDED.")
                    mBTDevice = mDevice
                }
                //case2: creating a bone
                if (mDevice?.bondState == BluetoothDevice.BOND_BONDING) {
                    Log.d(LOG_TAG, "BroadcastReceiver: BOND_BONDING.")
                }
                //case3: breaking a bond
                if (mDevice?.bondState == BluetoothDevice.BOND_NONE) {
                    Log.d(LOG_TAG, "BroadcastReceiver: BOND_NONE.")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == BluetoothConnectionService.MESSAGE_READ) {
                    binding.dataRead.text = msg.obj as String
                }
            }
        }

        bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter //getBluetoothAdapter()

        if (bluetoothAdapter == null) {
            Log.i(LOG_TAG, "Bluetooth is not supported")
        } else {
            binding.run {
                enableBtBtn.setOnClickListener { requestBluetoothPermission() }
                disableBtBtn.setOnClickListener { disableBluetooth() }
                enableDiscoverabilityBtn.setOnClickListener { enableDiscoverability() }
                discoverBtn.setOnClickListener { discoverDevices() }
                connectBtn.setOnClickListener { startConnection() }
                sendDataBtn.setOnClickListener { sendData() }
                getDeviceDataBtn.setOnClickListener { getDeviceData() }
                deviceList.setOnItemClickListener { _, _, position, _ -> createBond(position) }
            }
            //Broadcasts when bond state changes (ie:pairing)
            val filter = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
            registerReceiver(broadcastReceiverForBonding, filter)
        }
    }

    private fun getDeviceData() {
        val bytes: ByteArray =
            "bas[{\"kullanici\": \"kullanici_adi\", \"komut\": \"veriAl\", \"rol\": \"admin\"}]son"
                .toByteArray(Charset.defaultCharset())
        Log.d(LOG_TAG, "getDeviceData called")
        mBluetoothConnection!!.write(bytes)
    }

    //create method for starting connection
    //***remember the conncction will fail and app will crash if you haven't paired first
    private fun startConnection() {
        startBTConnection(mBTDevice, MY_UUID_INSECURE)
    }

    private fun sendData() {
        val bytes: ByteArray =
            binding.dataText.text.toString().toByteArray(Charset.defaultCharset())
        mBluetoothConnection!!.write(bytes)
    }

    private fun createBond(position: Int) {
        //first cancel discovery because its very memory intensive.
        bluetoothAdapter?.cancelDiscovery()

        Log.d(LOG_TAG, "onItemClick: You Clicked on a device.")
        val deviceName: String = btDevices[position].name
        val deviceAddress: String = btDevices[position].address

        Log.d(LOG_TAG, "onItemClick: deviceName = $deviceName")
        Log.d(LOG_TAG, "onItemClick: deviceAddress = $deviceAddress")

        //create the bond.
        btDevices[position].createBond()
        mBTDevice = btDevices[position]
//        mBluetoothConnection =
//            bluetoothAdapter?.let { BluetoothConnectionService(this, it, handler) }

    }

    private fun discoverDevices() {
        if (bluetoothAdapter?.isEnabled == false) {
            Toast.makeText(this, "Enable bluetooth first", Toast.LENGTH_LONG).show()

        } else {
            Log.i(LOG_TAG, "discoverDevices -> Looking for unpaired devices..")

            if (bluetoothAdapter?.isDiscovering == true) {
                bluetoothAdapter?.cancelDiscovery()
                Log.i(LOG_TAG, "discoverDevices -> Cancelling discovering...")

                checkBTPermissions()
                btDevices.clear()

                bluetoothAdapter?.startDiscovery()
                val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                registerReceiver(broadcastReceiverForDiscoveringDevices, intentFilter)
            } else {
                checkBTPermissions()
                btDevices.clear()

                bluetoothAdapter?.startDiscovery()
                val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                registerReceiver(broadcastReceiverForDiscoveringDevices, intentFilter)
            }
        }
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private fun checkBTPermissions() {
//        var permissionCheck = checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION")
//        permissionCheck += checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION")
//        if (permissionCheck != 0) {
//            requestPermissions(
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ), 1001
//            ) //Any number
//        }
    }

    private fun enableDiscoverability() {
        Log.i(LOG_TAG, "Making device discoverable for 300 seconds.")
        if (bluetoothAdapter?.isEnabled == false) {
            Toast.makeText(this, "Enable bluetooth first", Toast.LENGTH_LONG).show()
        } else {
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            startActivity(discoverableIntent)

            val intentFilter = IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
            registerReceiver(broadcastReceiverForBtDiscoverability, intentFilter)
        }
    }

    // ??
    private fun getBluetoothAdapter(): BluetoothAdapter? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) bluetoothManager.adapter
        else BluetoothAdapter.getDefaultAdapter()


    private fun disableBluetooth() {
        if (bluetoothAdapter?.isEnabled == true) {
            bluetoothAdapter?.disable()
            val btIntentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            registerReceiver(broadcastReceiverForBtState, btIntentFilter)
        }
    }

    private fun enableBluetooth() {
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            bluetoothActivityResultLauncher.launch(enableBtIntent)
            val btIntentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
            registerReceiver(broadcastReceiverForBtState, btIntentFilter)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_ENABLE_BT) {
//            if (resultCode == RESULT_OK) {
//                Log.i(LOG_TAG, "onActivityResult -> Allow clicked")
//            } else {
//                Log.i(LOG_TAG, "onActivityResult -> Deny clicked")
//            }
//        }
//
//    }

    private fun requestBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestMultiplePermissions.launch(
                arrayOf(
                    *bluetoothPermissionsLegacy,
                    *bluetoothPermissionsApi31
                )
            )
        } else {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestBluetooth.launch(enableBtIntent)
        }
    }


    private fun areBluetoothPermissionsGranted(): Boolean {
        val bluetoothPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            bluetoothPermissionsApi31
        } else {
            bluetoothPermissionsLegacy
        }
        for (permission in bluetoothPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }


    private fun isPermissionGranted(permission: String) = ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED

//    private fun isBluetoothPermissionGranted() = ActivityCompat.checkSelfPermission(
//        this,
//        Manifest.permission.BLUETOOTH_CONNECT
//    ) == PackageManager.PERMISSION_GRANTED


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//    }
    /**
     * starting bluetooth service method
     */
    private fun startBTConnection(device: BluetoothDevice?, uuid: UUID?) {
        if (device != null) {
            Log.d(LOG_TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.")
            mBluetoothConnection?.startClient(device, uuid)
        } else {
            Log.d(
                LOG_TAG,
                "startBTConnection: cant initialize RFCOM Bluetooth Connection since device is null"
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiverForBtState)
        unregisterReceiver(broadcastReceiverForBtDiscoverability)
        unregisterReceiver(broadcastReceiverForDiscoveringDevices)
        unregisterReceiver(broadcastReceiverForBonding)
    }
}
