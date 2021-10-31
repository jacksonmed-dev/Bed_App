package com.jacksonmed.bed.utils.bluetooth

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.MutableLiveData
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.api.checkBluetoothResponse
import com.jacksonmed.bed.utils.bluetooth.BluetoothConstants.Companion.BED_DATA_RESPONSE
import com.jacksonmed.bed.utils.bluetooth.BluetoothConstants.Companion.LAST_CHAR
import com.jacksonmed.bed.utils.bluetooth.BluetoothConstants.Companion.TEST_CHAR_RESPONSE
import com.jacksonmed.bed.utils.bluetooth.HelperFunctions.Companion.removeBytePadding

class BluetoothHandler(bluetoothResponse: MutableLiveData<BluetoothResponse<String>>): Handler(Looper.getMainLooper()) {
    var bluetoothString: String = ""
    var switchChar: String = ""
    val switchChars: Array<String> = arrayOf("!", "@")
    var bluetoothResponse = bluetoothResponse

    var isFirstChar: Boolean = false

    override fun handleMessage(msg: Message) {
        val data: ByteArray = removeBytePadding(msg.obj as ByteArray)
        var message: String = String(data)

        if(message.length == 0) {
            bluetoothResponse.postValue(checkBluetoothResponse("Hello World"))
            return
        }


        var firstChar: String = message.substring(0,1)
        var messageLastChar: String = message.takeLast(1)

        if(firstChar in switchChars){
            switchChar = firstChar
            if(messageLastChar.equals(LAST_CHAR))
                bluetoothString = bluetoothString.plus(message.drop(1))
                bluetoothResponse.postValue(checkBluetoothResponse(bluetoothString))
                bluetoothString = ""
                switchChar = ""
            return
        }

        if (messageLastChar.equals(LAST_CHAR)){
            if(!(firstChar in switchChars))
                bluetoothString = bluetoothString.plus(message.dropLast(1))
            else
                bluetoothString = bluetoothString.dropLast(1)
            when(switchChar) {
                "!" -> {
                    if (messageLastChar.equals(LAST_CHAR)) {
                        val temp = bluetoothString
                        bluetoothString = bluetoothString.drop(1).dropLast(1)
                        val result: List<Int> = bluetoothString.split(", ").map { it.toInt()}
//                        calculateBedBitMap(result)
                        bluetoothString = ""
                        switchChar = ""
                        bluetoothResponse.postValue(checkBluetoothResponse(bluetoothString))
                        return
                    }

                }
                "@" -> {
                    if ("*".equals(messageLastChar)) {
                        bluetoothString = ""
                        switchChar = ""
                        return
                    }
                }
            }
        }

        bluetoothString = bluetoothString.plus(message)
        // send the data to UI
    }

    fun handleBluetoothResponse(response: BluetoothResponse<String>) {
        var firstChar: String? = response.response?.substring(0,1) ?: null
        var messageLastChar: String? = response.response?.takeLast(1) ?: null

        when(switchChar) {
            BED_DATA_RESPONSE -> {
                if (messageLastChar.equals(LAST_CHAR)) {
                    val temp = bluetoothString
                    bluetoothString = bluetoothString.drop(1).dropLast(1)
                    val result: List<Int> = bluetoothString.split(", ").map { it.toInt()}
//                        calculateBedBitMap(result)
                    bluetoothString = ""
                    switchChar = ""
                    bluetoothResponse.postValue(checkBluetoothResponse(bluetoothString))
                    return
                }
            }
            TEST_CHAR_RESPONSE -> {
                if (messageLastChar.equals(LAST_CHAR)) {
                    bluetoothResponse.postValue(checkBluetoothResponse(bluetoothString))
                    bluetoothString = ""
                    switchChar = ""
                    return
                }
            }
        }
    }
}