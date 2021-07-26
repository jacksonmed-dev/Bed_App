package com.jacksonmed.bed.activities.overview.bed

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksonmed.bed.activities.overview.bed.Drawable.DrawableFragment
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.StatusResponse
import com.jacksonmed.bed.repository.RepositoryBed
import kotlinx.coroutines.launch
import retrofit2.Response

class BedViewModel(private val repository: RepositoryBed):ViewModel(){
    val startMassageResponse: MutableLiveData<Response<StatusResponse>> = MutableLiveData()
    fun startMassage(){
        viewModelScope.launch {
            val response: Response<StatusResponse> = repository.startMassage()
            startMassageResponse.value = response
        }
    }

    val stopMassageResponse: MutableLiveData<Response<StatusResponse>> = MutableLiveData()
    fun stopMassage(){
        viewModelScope.launch {
            val response: Response<StatusResponse> = repository.stopMassage()
            stopMassageResponse.value = response
        }
    }

    val bedStatusResponse: MutableLiveData<Response<Bed>> = MutableLiveData()

//    val getBedStatusResponse: LiveData<Response<Bed>> get() = bedStatusResponse

    fun getBedStatus(){
        viewModelScope.launch {
            val response: Response<Bed> = repository.getBedStatus()
            bedStatusResponse.value = response
        }
    }

    fun setBedDrawable(response: Response<Bed>, drawableFragment: DrawableFragment, context: Context){
       repository.setBedDrawable(response, drawableFragment, context)
    }
}