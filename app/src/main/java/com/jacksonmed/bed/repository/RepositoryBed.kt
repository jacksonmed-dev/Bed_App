package com.jacksonmed.bed.repository

import com.jacksonmed.bed.api.RetrofitInstance
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.StatusResponse
import retrofit2.Response

class RepositoryBed {
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