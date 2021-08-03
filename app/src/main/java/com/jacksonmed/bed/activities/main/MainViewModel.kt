package com.jacksonmed.bed.activities.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.Patient
import com.jacksonmed.bed.model.Post
import com.jacksonmed.bed.model.StatusResponse
import com.jacksonmed.bed.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository):ViewModel() {
    val myResponse: MutableLiveData<Response<Post>> = MutableLiveData()
    fun getPost(){
        viewModelScope.launch {
            val response:Response<Post> = repository.getPost()
            myResponse.value = response
        }
    }

    val raspPiResponse: MutableLiveData<Response<StatusResponse>> = MutableLiveData()
    fun getResponse(){
        viewModelScope.launch {
            val response:Response<StatusResponse> = repository.getResponse()
            raspPiResponse.value = response
        }
    }

//    val patientResponse: MutableLiveData<Response<Patient>> = MutableLiveData()
//    fun getPatientInfo(){
//        viewModelScope.launch {
//            val response:Response<Patient> = repository.getPatientInfo()
//            patientResponse.value = response
//        }
//    }

    val massageStartResponse: MutableLiveData<Response<StatusResponse>> = MutableLiveData()
    fun startMassage(){
        viewModelScope.launch {
            val response:Response<StatusResponse> = repository.startMassage()
            massageStartResponse.value = response
        }
    }

    val massageStopResponse: MutableLiveData<Response<StatusResponse>> = MutableLiveData()
    fun stopMassage(){
        viewModelScope.launch {
            val response:Response<StatusResponse> = repository.stopMassage()
            massageStopResponse.value = response
        }
    }

    val bedStatusResponse: MutableLiveData<Response<Bed>> = MutableLiveData()
    fun getBedStatus(){
        viewModelScope.launch {
            val response: Response<Bed> = repository.getBedStatus()
            bedStatusResponse.value = response
        }
    }

}