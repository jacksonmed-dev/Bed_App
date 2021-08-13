package com.jacksonmed.bed.activities.overview.bed

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksonmed.bed.api.ApiResponse
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.repository.RepositoryBed
import kotlinx.coroutines.launch
import retrofit2.Response

class DrawableFragmentViewModel(private val repository: RepositoryBed):ViewModel(){
    val bedStatusResponse: MediatorLiveData<ApiResponse<Bed>> = MediatorLiveData()
    fun getBedStatus(): MediatorLiveData<ApiResponse<Bed>>{
        viewModelScope.launch {
            bedStatusResponse.addSource(repository.getBedStatus()) { response ->
                bedStatusResponse.value = response
            }
        }
        return bedStatusResponse
    }
}