package com.jacksonmed.bed.activities.overview.patient

import androidx.lifecycle.*
import com.jacksonmed.bed.api.ApiResponse
import com.jacksonmed.bed.model.Patient
import com.jacksonmed.bed.model.PatientPressure
import com.jacksonmed.bed.repository.RepositoryPatient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class PatientViewModel(private val repository: RepositoryPatient) : ViewModel() {
    val maxPressure: MediatorLiveData<ApiResponse<PatientPressure>> = MediatorLiveData()
    fun getMaxPressure(): LiveData<ApiResponse<PatientPressure>> {
        viewModelScope.launch {
            maxPressure.addSource(repository.getPatientPressure()) { response ->
                maxPressure.value = response
            }
        }
        return maxPressure
    }



    val patientInfoResponse: MediatorLiveData<ApiResponse<Patient>> = MediatorLiveData()
    fun getPatientInfo(): LiveData<ApiResponse<Patient>> {
        viewModelScope.launch {
            patientInfoResponse.addSource(
                repository.getPatientInfo()
            ) { response ->
                patientInfoResponse.value = response
            }
        }
        return patientInfoResponse
    }

}