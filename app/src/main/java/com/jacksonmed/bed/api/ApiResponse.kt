package com.jacksonmed.bed.api
import androidx.lifecycle.MutableLiveData
import com.jacksonmed.bed.model.Patient
import retrofit2.Response


data class ApiResponse<T>(
    val response: Response<T>?,
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



