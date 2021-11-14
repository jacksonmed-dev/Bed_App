package com.jacksonmed.bed.utils.bluetooth

import android.os.Message
import androidx.lifecycle.MutableLiveData
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.utils.bluetooth.HelperFunctions.Companion.generateBluetoothByteArray
import com.jacksonmed.bed.utils.bluetooth.util.BluetoothConstants.Companion.MASSAGE_HEADER
import org.junit.Before
import org.junit.Test

internal class BluetoothHandlerTest {
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private val batchSize: Int = 1024
    private lateinit var bluetoothHandler: BluetoothHandler
    private var bluetoothResult: MutableLiveData<BluetoothResponse<String>> = MutableLiveData()

    @Before
    fun setup(){
        bluetoothHandler = BluetoothHandler(bluetoothResult)
    }

    @Test
    fun handleBluetoothResponseShort() {
        val data = generateBluetoothByteArray(MASSAGE_HEADER,"TEST")
        val msg: Message = Message()
        msg.obj = data
        bluetoothHandler.handleMessage(msg)
    }

    @Test
    fun handleBluetoothResponseMedium() {
        splitMessageBatch(600)
    }

    @Test
    fun handleBluetoothResponseLong() {
        splitMessageBatch(2000)
    }

    fun splitMessageBatch(x: Int){
        val data = generateBluetoothByteArray(MASSAGE_HEADER, generateRandomString(x))
        val messageCount: Int = data.size/batchSize
        for (i in 0..messageCount){
            val msg: Message = Message()
            msg.obj = if (i == messageCount) data.copyOfRange(i*batchSize, data.size) else data.copyOfRange(i * batchSize, (i + 1) * batchSize)
            bluetoothHandler.handleMessage(msg)
        }
    }

    fun generateRandomString(x: Int): String {
        return (1..x)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size)}
            .map (charPool::get)
            .joinToString { "" }
    }
}