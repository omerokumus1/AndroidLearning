package com.example.androidlearning
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class DeviceListAdapter(
    context: Context,
    private val mViewResourceId: Int,
    private val mDevices: List<BluetoothDevice>
) :
    ArrayAdapter<BluetoothDevice?>(context, mViewResourceId, mDevices) {
    private val mLayoutInflater: LayoutInflater

    init {
        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    @SuppressLint("MissingPermission")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView = mLayoutInflater.inflate(mViewResourceId, null)
        val device = mDevices[position]

        val deviceName = convertView.findViewById<View>(R.id.tvDeviceName) as TextView
        val deviceAdress = convertView.findViewById<View>(R.id.tvDeviceAddress) as TextView
        deviceName.text = device.name ?: "Unknown Device"
        deviceAdress.text = device.address ?: "No address available"
        return convertView
    }
}