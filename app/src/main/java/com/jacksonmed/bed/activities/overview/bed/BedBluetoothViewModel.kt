package com.jacksonmed.bed.activities.overview.bed

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.*
import com.jacksonmed.bed.utils.HelperFunctions.Companion.removeBytePadding
import com.jacksonmed.bed.utils.PressureBitmap


class BedBluetoothViewModel():ViewModel(){
    val bedDataResponse: MutableLiveData<String> = MutableLiveData()
    val bedDataBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    val bluetoothResponse: MediatorLiveData<String> = MediatorLiveData()

    val handler = object : Handler(Looper.getMainLooper()) {
        var bluetoothString: String = ""
        var switchChar: String = ""
        val switchChars: Array<String> = arrayOf("!", "@")
        val lastChar: String = "*"
        override fun handleMessage(msg: Message) {
            val data: ByteArray = removeBytePadding(msg.obj as ByteArray)
            var message: String = String(data)

            if(message.length == 0) return

            var firstChar: String = message.substring(0,1)
            var messageLastChar: String = message.takeLast(1)

            if(firstChar in switchChars){
                switchChar = firstChar
                bluetoothString = bluetoothString.plus(message.drop(1))
            }

            if (lastChar.equals(messageLastChar)){
                if(!(firstChar in switchChars))
                    bluetoothString = bluetoothString.plus(message.dropLast(1))
                else
                    bluetoothString = bluetoothString.dropLast(1)
                when(switchChar) {
                    "@" -> {
                        if ("]" in message) {
                            bluetoothString = bluetoothString.plus(message)
                            val temp = bluetoothString
                            val result: List<Int> = bluetoothString.removeSurrounding("[", "]").split(", ").map { it.toInt()}
                            calculateBedBitMap(result)
                            bluetoothResponse.value = bluetoothString
                            bluetoothString = ""
                            switchChar = ""
                            return
                        }

                    }
                    "!" -> {
                        bluetoothResponse.value = bluetoothString
                        bluetoothString = ""
                        switchChar = ""
                        return
                    }
                }
            }

            bluetoothString = bluetoothString.plus(message)

            // send the data to UI
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