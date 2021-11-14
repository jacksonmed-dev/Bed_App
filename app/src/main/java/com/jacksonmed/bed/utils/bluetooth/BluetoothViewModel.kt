package com.jacksonmed.bed.utils.bluetooth

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.*
import com.google.gson.Gson
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.api.checkBluetoothResponse
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.BedStatus
import com.jacksonmed.bed.utils.PressureBitmap
import com.jacksonmed.bed.utils.bluetooth.util.BluetoothConstants
import java.lang.Exception


class BluetoothViewModel():ViewModel(){
    var bluetoothString: String = ""
    var switchChar: String = ""
    val switchChars: Array<String> = arrayOf("!", "@")

    val bluetoothResponse: MediatorLiveData<BluetoothResponse<String>> = MediatorLiveData()
    val bedDataBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    val bedStatusResponse: MediatorLiveData<Bed> = MediatorLiveData()
//    fun simple(): Flow<Int> = flow {
//        println("Flow started")
//        for (i in 1..3) {
//            delay(100)
//            emit(i)
//        }
//    }

    fun handleBluetooth(data: ByteArray){
        val data: ByteArray = HelperFunctions.removeBytePadding(data)
        var message: String = String(data)

        if(message.length == 0) {
            return
        }

        var firstChar: String = message.substring(0,1)
        var messageLastChar: String = message.takeLast(1)

        if(firstChar in switchChars){
            switchChar = firstChar
            if(messageLastChar.equals(BluetoothConstants.TRAILER)) {
                bluetoothString = bluetoothString.plus(message)
                handleBluetoothResponse(bluetoothString)
                bluetoothString = ""
                switchChar = ""
            }else
                bluetoothString = bluetoothString.plus(message)
            return
        }

        if (messageLastChar.equals(BluetoothConstants.TRAILER)){
            bluetoothString = bluetoothString.plus(message)
            handleBluetoothResponse(bluetoothString)
            bluetoothString = ""
            switchChar = ""
            return
        }

        bluetoothString = bluetoothString.plus(message)
        // send the data to UI

    }

    fun handleBluetoothResponse(response: String) {
        var firstChar: String? = response.substring(0,1)
        var messageLastChar: String? = response.takeLast(1)

        when(firstChar) {
            BluetoothConstants.BED_DATA_RESPONSE -> {
                if (messageLastChar.equals(BluetoothConstants.TRAILER)) {
                    val temp = bluetoothString
                    bluetoothString = bluetoothString.drop(2).dropLast(2)       // Removes header, trailer, and brackets
                    val result: List<Int> = bluetoothString.split(", ").map { it.toInt()}
                    calculateBedBitMap(result)
                    bluetoothString = ""
                    switchChar = ""
                    return
                }
            }
            BluetoothConstants.TEST_CHAR_RESPONSE -> {
                if (messageLastChar.equals(BluetoothConstants.TRAILER)) {
                    bluetoothResponse.postValue(checkBluetoothResponse(bluetoothString))
                    bluetoothString = ""
                    switchChar = ""
                    return
                }
            }
            BluetoothConstants.BED_STATUS_RESPONSE -> {
                bluetoothString = bluetoothString.drop(1).dropLast(1)
                try {
                    val newData: Bed = Gson().fromJson(bluetoothString, Bed::class.java)
                    bedStatusResponse.postValue(newData)
                }catch (e: Exception){
                    e.printStackTrace()
                }
                return
            }
        }
    }

    fun calculateBedBitMap(data: List<Int>) {
//        var readings: JSONArray = JSONObject(message!!).getJSONArray("readings").getJSONArray(0)
        var readingsArray: IntArray = IntArray(data.size) { 0 }
        for(i in 0 until data.size){
            when(data.get(i)){
                in 0..10 -> readingsArray[i] = Color.BLACK
                in 10..30 -> readingsArray[i] = Color.BLUE
                in 30..50 -> readingsArray[i] = Color.GREEN
                in 50..70 -> readingsArray[i] = Color.YELLOW
                in 70..90 -> readingsArray[i] = Color.RED
                else -> readingsArray[i] = Color.WHITE
            }
        }

        var bitmap: Bitmap = PressureBitmap().pressureMap(readingsArray, 27, 64)
        var finalBitmap: Bitmap = PressureBitmap().scaleBitmap(bitmap, 12f)!!
        bedDataBitmap.postValue(finalBitmap)
//        bedDataResponse.postValue(message!!)
    }


    fun useRegex(input: String): Boolean {
        val regex = Regex(pattern = "10\\.0\\.0\\.2", options = setOf(RegexOption.IGNORE_CASE))
        val result: Boolean = regex.matches(input)
        return result
    }
}