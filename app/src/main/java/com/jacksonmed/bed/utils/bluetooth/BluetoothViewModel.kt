package com.jacksonmed.bed.utils.bluetooth

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.*
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.utils.PressureBitmap


class BluetoothViewModel():ViewModel(){
    val bedDataResponse: MutableLiveData<String> = MutableLiveData()
    val bedDataBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    val bluetoothResponse: MediatorLiveData<BluetoothResponse<String>> = MediatorLiveData()

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