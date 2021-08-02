package com.jacksonmed.bed.activities.overview.patient

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacksonmed.bed.model.Patient
import com.jacksonmed.bed.model.PatientPressure
import com.jacksonmed.bed.repository.RepositoryPatient
import kotlinx.coroutines.launch
import retrofit2.Response

class PatientViewModel(private val repository: RepositoryPatient) : ViewModel() {
    val maxPressure: MutableLiveData<Response<PatientPressure>> = MutableLiveData()
    fun getMaxPressure(){
        viewModelScope.launch {
            val response: Response<PatientPressure> = repository.getPatientPressure()
            maxPressure.value = response
        }
    }

    val patientInfo: MutableLiveData<Response<Patient>> = MutableLiveData()
    fun getPatientInfo() {
        viewModelScope.launch {
            val response: Response<Patient> = repository.getPatientInfo()
            patientInfo.value = response
        }
    }

}