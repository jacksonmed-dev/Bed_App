package com.jacksonmed.bed.utils.bluetooth.service

import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jacksonmed.bed.utils.bluetooth.BluetoothViewModel
import com.jacksonmed.bed.utils.bluetooth.HelperFunctions
import com.jacksonmed.bed.utils.bluetooth.util.BluetoothConstants
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

private const val TAG = "MY_APP_DEBUG_TAG"
// https://developer.android.com/guide/topics/connectivity/bluetooth/transfer-data
// Defines several constants used when transmitting messages between the
// service and the UI.
const val MESSAGE_READ: Int = 0
const val MESSAGE_WRITE: Int = 1
const val MESSAGE_TOAST: Int = 2
// ... (Add other message types here as needed.)

@AndroidEntryPoint
class MyBluetoothService @Inject constructor(
    // handler that gets info from Bluetooth service
//    private val handler: Handler,
//    var context: Context,
): AppCompatActivity() {
    val m_address: String = BluetoothConstants.BLUETOOTH_ADDRESS
    lateinit var handler: Handler
    lateinit var m_bluetoothSocket: BluetoothSocket
    lateinit var m_bluetoothService: ConnectedThread

    val bluetoothViewModel: BluetoothViewModel by viewModels()

    fun processFinish(m_bluetoothSocket: BluetoothSocket) {
        if(m_bluetoothSocket != null){
            this.m_bluetoothSocket = m_bluetoothSocket
            handler = Handler(Looper.getMainLooper())
            m_bluetoothService = ConnectedThread(this.handler!!, this.m_bluetoothSocket!!, bluetoothViewModel::handleBluetooth)
            m_bluetoothService.start()
        }
    }

    fun connect(){
        val connect = ConnectToDevice(applicationContext, ::processFinish, m_address)
        connect.execute()
    }

    fun sendMessage(data: ByteArray){
        if(m_bluetoothService != null){
            m_bluetoothService.write(data)
        }
    }

}