package com.jacksonmed.bed.activities.overview.bed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.repository.RepositoryBed
import kotlinx.coroutines.launch
import retrofit2.Response

class DrawableFragmentViewModel(private val repository: RepositoryBed):ViewModel(){
    val bedStatusResponse: MutableLiveData<Response<Bed>> = MutableLiveData()
    fun getBedStatus(){
        viewModelScope.launch {
            val response: Response<Bed> = repository.getBedStatus()
            bedStatusResponse.value = response
        }
    }
}