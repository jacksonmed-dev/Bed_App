package com.jacksonmed.bed.repository

import androidx.lifecycle.MutableLiveData
import com.jacksonmed.bed.api.ApiResponse
import com.jacksonmed.bed.api.RetrofitInstance
import com.jacksonmed.bed.api.checkApiResponse
import com.jacksonmed.bed.model.Patient
import com.jacksonmed.bed.model.PatientPressure
import retrofit2.Response

class RepositoryPatient {
    suspend fun getPatientPressure(): MutableLiveData<ApiResponse<PatientPressure>> {
        var apiResponse: Response<PatientPressure> = RetrofitInstance.api.getMaxPressure()
        return checkApiResponse(apiResponse)
    }


    suspend fun getPatientInfo(): MutableLiveData<ApiResponse<Patient>> {
        var apiResponse: Response<Patient> = RetrofitInstance.api.getPatientInfo()
        return checkApiResponse(apiResponse)
    }
}