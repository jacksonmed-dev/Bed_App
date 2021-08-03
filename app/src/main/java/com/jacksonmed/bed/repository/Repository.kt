package com.jacksonmed.bed.repository

import com.jacksonmed.bed.api.RetrofitInstance
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.Patient
import com.jacksonmed.bed.model.Post
import com.jacksonmed.bed.model.StatusResponse
import retrofit2.Response

class Repository {
    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getPost()
    }

    suspend fun getResponse(): Response<StatusResponse> {
        return RetrofitInstance.api.getResponse()
    }

//    suspend fun getPatientInfo(): Response<Patient> {
//        return RetrofitInstance.api.getPatientInfo()
//    }

    suspend fun startMassage(): Response<StatusResponse> {
        return RetrofitInstance.api.startMassage()
    }

    suspend fun stopMassage(): Response<StatusResponse> {
        return RetrofitInstance.api.stopMassage()
    }

    suspend fun getBedStatus(): Response<Bed> {
        return RetrofitInstance.api.getBedStatus()
    }
}