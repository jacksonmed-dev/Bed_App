package com.example.bluetoothdemo.bluetooth

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.lang.Exception
import java.util.*

class ConnectToDevice(private val context: Context,
                      private val listener: (m_bluetoothSocket: BluetoothSocket) -> Unit,
                      private val m_address: String) : AsyncTask<Void, Void, String>() {
    var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    var m_bluetoothSocket: BluetoothSocket? = null
    lateinit var m_progress: ProgressDialog
    lateinit var m_bluetoothAdapter: BluetoothAdapter
    var m_isConnected: Boolean = false
    private var connectSuccess: Boolean = true

    override fun onPreExecute() {
        super.onPreExecute()
        m_progress = ProgressDialog.show(context, "Connecting...", "wait")
    }

    override fun doInBackground(vararg params: Void?): String? {
        try {
            if (m_bluetoothSocket == null || !m_isConnected) {
                m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(
                    m_address
                )
                m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(
                    m_myUUID
                )
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                m_bluetoothSocket!!.connect()
            }

        } catch (e: Exception) {
            connectSuccess = false
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (!connectSuccess) {
            Log.i("data", "Could not connect")
        } else {
            m_isConnected = true
        }
        listener(m_bluetoothSocket!!)
        m_progress.dismiss()
    }
}