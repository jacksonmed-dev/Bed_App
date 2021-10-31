package com.jacksonmed.bed.api
import androidx.lifecycle.MutableLiveData
import com.jacksonmed.bed.model.Patient
import retrofit2.Response


data class ApiResponse<T>(
    val response: Response<T>?,
    val error: String?
)

data class BluetoothResponse<T>(
    val response: T?,
    val error: String?
)

inline fun <reified T> checkApiResponse(response: Response<T>): MutableLiveData<ApiResponse<T>> {
    val responseLiveData: MutableLiveData<ApiResponse<T>> = MutableLiveData<ApiResponse<T>>()
    var exception: String? = null

    if(response.body() is T){
        responseLiveData.postValue(ApiResponse(response, exception))
    }else {
        responseLiveData.postValue(ApiResponse(null, response.message()))
    }
    return responseLiveData
}

inline fun <reified T> checkBluetoothResponse(response: T): BluetoothResponse<T> {
    val bluetoothResponse: BluetoothResponse<T>
    var exception: String? = null

    if(response is T){
        bluetoothResponse = BluetoothResponse(response, exception)
    }else {
        bluetoothResponse = BluetoothResponse(null, "Invalid bluetooth response. No value received")
    }
    return bluetoothResponse
}



