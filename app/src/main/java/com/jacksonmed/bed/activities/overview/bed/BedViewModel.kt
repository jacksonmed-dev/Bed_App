package com.jacksonmed.bed.activities.overview.bed

import android.content.Context
import androidx.lifecycle.*
import com.jacksonmed.bed.api.ApiResponse
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.PatientPressure
import com.jacksonmed.bed.model.StatusResponse
import com.jacksonmed.bed.repository.RepositoryBed
import kotlinx.coroutines.launch
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
}