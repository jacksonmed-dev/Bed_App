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
import com.example.bluetoothdemo.bluetooth.MyBluetoothService
import com.here.oksse.OkSse
import com.here.oksse.ServerSentEvent
import com.jacksonmed.bed.api.ApiResponse
import com.jacksonmed.bed.api.BluetoothResponse
import com.jacksonmed.bed.api.SseSample
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.PatientPressure
import com.jacksonmed.bed.model.StatusResponse
import com.jacksonmed.bed.repository.RepositoryBed
import com.jacksonmed.bed.utils.Constants
import com.jacksonmed.bed.utils.Constants.Companion.SENSOR_URL
import com.jacksonmed.bed.utils.PressureBitmap
import com.jacksonmed.bed.utils.SseListener
import kotlinx.coroutines.launch
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import java.util.*

class BedViewModel(private val repository: RepositoryBed):ViewModel(){

    val bluetoothResponse: MediatorLiveData<BluetoothResponse<String>> = MediatorLiveData()

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

    val bedDataBitmap: MutableLiveData<Bitmap> = MutableLiveData()
    fun getBedData() {
        viewModelScope.launch {
            val request: Request = Request.Builder().url(SENSOR_URL).build()
            val sse = OkSse().newServerSentEvent(request, SseListener())
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
}