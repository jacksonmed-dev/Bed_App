package com.jacksonmed.bed.activities.overview.bed

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
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

class BedViewModel(private val repository: RepositoryBed):ViewModel(){
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
                        var readings: JSONArray = JSONObject(message!!).getJSONArray("readings").getJSONArray(0)
                        var readingsArray: IntArray = IntArray(readings.length()) { 0 }
                        for(i in 0 until readings.length()){
                            when(readings.getInt(i)){
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
                        bedDataResponse.postValue(message!!)
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
}