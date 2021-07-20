package com.jacksonmed.bed.api

import com.jacksonmed.bed.utils.Constants.Companion.BASE_URL
import com.jacksonmed.bed.utils.Constants.Companion.RASPI_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(RASPI_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}