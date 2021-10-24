package com.jacksonmed.bed.activities.overview.bed

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.*
import com.here.oksse.OkSse
import com.here.oksse.ServerSentEvent
import com.jacksonmed.bed.api.ApiResponse
import com.jacksonmed.bed.api.SseSample
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.PatientPressure
import com.jacksonmed.bed.model.StatusResponse
import com.jacksonmed.bed.repository.RepositoryBed
import com.jacksonmed.bed.utils.Constants
import com.jacksonmed.bed.utils.Constants.Companion.SENSOR_URL
import com.jacksonmed.bed.utils.PressureBitmap
import kotlinx.coroutines.launch
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import java.util.*

class BedBluetoothViewModel(private val repository: RepositoryBed):ViewModel(){
    val startMassageResponse: MediatorLiveData<ApiResponse<StatusResponse>> = MediatorLiveData()
    fun startMassage(): LiveData<ApiResponse<StatusResponse>> {
        viewModelScope.launch {
            startMassageResponse.addSource(repository.startMassage()) {response ->
                startMassageResponse.value = response
            }
        }
        return startMassageResponse
    }

    val stopMassageResponse: MediatorLiveData<Response<StatusResponse>> = MediatorLiveData()
    fun stopMassage(): LiveData<ApiResponse<StatusResponse>> {
        viewModelScope.launch {
            startMassageResponse.addSource(repository.stopMassage()) {response ->
                startMassageResponse.value = response
            }
        }
        return startMassageResponse
    }

    val bedStatusResponse: MediatorLiveData<ApiResponse<Bed>> = MediatorLiveData()
    fun getBedStatus(): LiveData<ApiResponse<Bed>>{
        viewModelScope.launch {
            bedStatusResponse.addSource(repository.getBedStatus()) { response ->
                bedStatusResponse.value = response

            }
        }
        return bedStatusResponse
    }

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

    val bedDataResponse: MutableLiveData<String> = MutableLiveData()
    val bedDataBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    fun getBedData() {
        viewModelScope.launch {
            val request: Request = Request.Builder().url(SENSOR_URL).build()
            val sse = OkSse().newServerSentEvent(request, object : ServerSentEvent.Listener {
                override fun onOpen(sse: ServerSentEvent?, response: okhttp3.Response?) {
                    Log.d("Open", "Connection Open")
                }

                override fun onMessage(
                    sse: ServerSentEvent?,
                    id: String?,
                    event: String?,
                    message: String?
                ) {
                    //Create Response and update live data
                    if (event == "newframe"){
//                        calculateBedBitMap(message)
                    }

                    Log.d("Open", message!!)
                }

                override fun onComment(sse: ServerSentEvent?, comment: String?) {
                    Log.d("Open", "Connection Open")
                }

                override fun onRetryTime(sse: ServerSentEvent?, milliseconds: Long): Boolean {
                    Log.d("Open", "Connection Open")
                    return true
                }

                override fun onRetryError(
                    sse: ServerSentEvent?,
                    throwable: Throwable?,
                    response: okhttp3.Response?
                ): Boolean {
                    Log.d("Open", "Connection Open")
                    return true
                }

                override fun onClosed(sse: ServerSentEvent?) {
                    Log.d("Open", "Connection Open")
                }

                // Change this function. I set the request to be null for demo purposes
                override fun onPreRetry(
                    sse: ServerSentEvent?,
                    originalRequest: Request?
                ): Request? {
                    var request: Request? = null
                    Log.d("Open", "Connection Open")
                    return request
                }

            })
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
//                            val temp = readings.getInt(i).toFloat()
//                            val x = temp/104
//                            val temp2 = x * 0xfffffff
//                            val final = temp2.toInt()
//                            readingsArray[i] = Color.GREEN
        }

        var bitmap: Bitmap = PressureBitmap().pressureMap(readingsArray, 27, 64)
        var finalBitmap: Bitmap = PressureBitmap().scaleBitmap(bitmap, 12f)!!
        bedDataBitmap.postValue(finalBitmap)
//        bedDataResponse.postValue(message!!)
    }

//    fun useRegex(input: String): Boolean {
//        val regex = Regex(pattern = "\\b(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))\\b", options = setOf(RegexOption.IGNORE_CASE))
//        return regex.matches(input)
//    }

    fun removeBytePadding(byteArray: ByteArray): ByteArray{
        var result = mutableListOf<Byte>()
        for(byte in byteArray){
            val b: Byte = byte
            val c: Byte = 0x0.toByte()
            if(b == c){
                return result.toByteArray()
            }
            result.add(byte)
        }
        return result.toByteArray()
    }
    fun useRegex(input: String): Boolean {
        val regex = Regex(pattern = "10\\.0\\.0\\.2", options = setOf(RegexOption.IGNORE_CASE))
        val result: Boolean = regex.matches(input)
        return result
    }
}