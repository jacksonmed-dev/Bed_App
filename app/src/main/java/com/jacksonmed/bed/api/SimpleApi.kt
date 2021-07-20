package com.jacksonmed.bed.api
import com.jacksonmed.bed.model.Bed
import com.jacksonmed.bed.model.Patient
import com.jacksonmed.bed.model.Post
import com.jacksonmed.bed.model.StatusResponse
import retrofit2.Response
import retrofit2.http.GET

interface SimpleApi {
    @GET("posts/1")
    suspend fun getPost(): Response<Post>

    @GET("/")
    suspend fun getResponse(): Response<StatusResponse>

    @GET("/patient/info")
    suspend fun getPatientInfo(): Response<Patient>

    @GET("/massage/start")
    suspend fun startMassage(): Response<StatusResponse>

    @GET("/massage/stop")
    suspend fun stopMassage(): Response<StatusResponse>

    @GET("/bed/status")
    suspend fun getBedStatus(): Response<Bed>
}