package com.jacksonmed.bed.utils.bluetooth.service

import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Handler

private const val TAG = "MY_APP_DEBUG_TAG"
// https://developer.android.com/guide/topics/connectivity/bluetooth/transfer-data
// Defines several constants used when transmitting messages between the
// service and the UI.
const val MESSAGE_READ: Int = 0
const val MESSAGE_WRITE: Int = 1
const val MESSAGE_TOAST: Int = 2
// ... (Add other message types here as needed.)

class MyBluetoothService(
    // handler that gets info from Bluetooth service
    private val handler: Handler,
    val m_address: String,
    val context: Context,
    private val callback: (result: ByteArray) -> Unit
) {
    lateinit var m_bluetoothSocket: BluetoothSocket
    lateinit var m_bluetoothService: ConnectedThread


    fun processFinish(m_bluetoothSocket: BluetoothSocket) {
        if(m_bluetoothSocket != null){
            this.m_bluetoothSocket = m_bluetoothSocket
            m_bluetoothService = ConnectedThread(this.handler!!, this.m_bluetoothSocket!!, callback)
            m_bluetoothService.start()
        }
    }

    fun connect(){
        val connect = ConnectToDevice(context, ::processFinish, m_address)
        connect.execute()
    }

    fun sendMessage(data: ByteArray){
        if(m_bluetoothService != null){
            m_bluetoothService.write(data)
        }
    }

}