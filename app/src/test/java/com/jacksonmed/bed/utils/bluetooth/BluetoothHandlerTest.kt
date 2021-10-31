package com.jacksonmed.bed.utils.bluetooth

import android.os.Message
import androidx.lifecycle.MutableLiveData
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.utils.bluetooth.HelperFunctions.Companion.generateBluetoothByteArray
import org.junit.Before
import org.junit.Test

internal class BluetoothHandlerTest {
    private lateinit var bluetoothHandler: BluetoothHandler
    private var bluetoothResult: MutableLiveData<BluetoothResponse<String>> = MutableLiveData()

    @Before
    fun setup(){
        bluetoothHandler = BluetoothHandler(bluetoothResult)
    }

    @Test
    fun handleBluetoothResponse() {
        val data = generateBluetoothByteArray("TEST")
        val msg: Message = Message()
        msg.obj = data
        bluetoothHandler.handleMessage(msg)
    }
}