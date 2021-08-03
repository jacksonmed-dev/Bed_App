package com.jacksonmed.bed.api
import okhttp3.*
import kotlin.Exception

class TimeOutInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            chain.proceed(request)
        } catch (e: Exception) {
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(599)
                .message(e.toString()!!)
                .body(ResponseBody.create(null, e.toString()!!))
                .build()
        }
    }
}