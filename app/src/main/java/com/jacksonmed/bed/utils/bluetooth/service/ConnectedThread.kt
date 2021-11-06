package com.jacksonmed.bed.utils.bluetooth.service

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ConnectedThread(private val handler: Handler, private val m_bluetoothSocket:BluetoothSocket) : Thread() {

    private lateinit var mmInStream: InputStream
    private lateinit var mmOutStream: OutputStream
    private var mmBuffer: ByteArray = ByteArray(1024) // mmBuffer store for the stream
    private var m_isConnected: Boolean = true
    private val TAG: String = "Connected Thread"

    init {
        if (m_bluetoothSocket != null && m_isConnected) {
            mmInStream = m_bluetoothSocket!!.inputStream
            mmOutStream = m_bluetoothSocket!!.outputStream
        }
    }


    override fun run() {
        if (m_bluetoothSocket != null && !m_isConnected) {
            return
        }

        var numBytes: Int // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            // Read from the InputStream.
             val mmBuffer2: ByteArray = ByteArray(1024) // mmBuffer store for the stream

            numBytes = try {
                mmInStream.read(mmBuffer)
            } catch (e: IOException) {
                Log.d(TAG, "Input stream was disconnected", e)
                break
            }

            // Send the obtained bytes to the UI activity.
            val readMsg = handler.obtainMessage(
                MESSAGE_READ, numBytes, -1,
                mmBuffer)
            mmBuffer = ByteArray(1024)
            readMsg.sendToTarget()
        }
    }

    // Call this from the main activity to send data to the remote device.
    fun write(bytes: ByteArray) {
        try {
            mmOutStream.write(bytes)
        } catch (e: IOException) {
            Log.e(TAG, "Error occurred when sending data", e)

            // Send a failure message back to the activity.
            val writeErrorMsg = handler.obtainMessage(MESSAGE_TOAST)
            val bundle = Bundle().apply {
                putString("toast", "Couldn't send data to the other device")
            }
            writeErrorMsg.data = bundle
            handler.sendMessage(writeErrorMsg)
            return
        }

        // Share the sent message with the UI activity.
        val writtenMsg = handler.obtainMessage(
            MESSAGE_WRITE, -1, -1, mmBuffer)
        writtenMsg.sendToTarget()
    }

    // Call this method from the main activity to shut down the connection.
    fun close() {
        try {
            m_bluetoothSocket!!.close()
            m_isConnected = false
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the connect socket", e)
        }
    }
}