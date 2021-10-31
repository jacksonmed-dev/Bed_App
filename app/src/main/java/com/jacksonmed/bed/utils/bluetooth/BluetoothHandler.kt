package com.jacksonmed.bed.utils.bluetooth

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.MutableLiveData
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.api.checkBluetoothResponse
import com.jacksonmed.bed.utils.HelperFunctions.Companion.removeBytePadding

class BluetoothHandler(bluetoothResponse: MutableLiveData<BluetoothResponse<String>>): Handler(Looper.getMainLooper()) {
    var bluetoothString: String = ""
    var switchChar: String = ""
    val switchChars: Array<String> = arrayOf("!", "@")
    val lastChar: String = "*"
    var response = bluetoothResponse
    override fun handleMessage(msg: Message) {
        val data: ByteArray = removeBytePadding(msg.obj as ByteArray)
        var message: String = String(data)

//        if(message.length == 0) return
        if(message.length == 0) {
            val temp = checkBluetoothResponse("Hello World")
            val temp2 = checkBluetoothResponse("Hello World")
            response.postValue(checkBluetoothResponse("Hello World"))
//            response.postValue(temp2)
            return
        }


        var firstChar: String = message.substring(0,1)
        var messageLastChar: String = message.takeLast(1)

        if(firstChar in switchChars){
            switchChar = firstChar
            bluetoothString = bluetoothString.plus(message.drop(1))
            return
        }

        if (lastChar.equals(messageLastChar)){
            if(!(firstChar in switchChars))
                bluetoothString = bluetoothString.plus(message.dropLast(1))
            else
                bluetoothString = bluetoothString.dropLast(1)
            when(switchChar) {
                "!" -> {
                    if ("*".equals(messageLastChar)) {
                        val temp = bluetoothString
                        bluetoothString = bluetoothString.drop(1).dropLast(1)
                        val result: List<Int> = bluetoothString.split(", ").map { it.toInt()}
//                        calculateBedBitMap(result)
                        bluetoothString = ""
                        switchChar = ""
                        response.postValue(checkBluetoothResponse(bluetoothString))
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
}