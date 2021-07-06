package com.jacksonmed.bed.repository

import com.jacksonmed.bed.api.RetrofitInstance
import com.jacksonmed.bed.model.Post
import retrofit2.Response

class Repository {
    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getPost()
    }
}