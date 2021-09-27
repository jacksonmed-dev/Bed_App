package com.jacksonmed.bed.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.here.oksse.OkSse
import com.here.oksse.ServerSentEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.logging.Logger
import kotlin.random.Random

class SseSample(url: String, data: MutableLiveData<String>) {
    val request: Request = Request.Builder().url(url).build()

    fun main() = runBlocking {
        withContext(Dispatchers.Default) {
            val sse = OkSse().newServerSentEvent(request, object : ServerSentEvent.Listener {
                override fun onOpen(sse: ServerSentEvent?, response: Response?) {
                    Log.d("Open", "Connection Open")
                }

                override fun onMessage(
                    sse: ServerSentEvent?,
                    id: String?,
                    event: String?,
                    message: String?
                ) {
                    //Create Response and update live data
                    Log.d("Open", message!!)
                }

                override fun onComment(sse: ServerSentEvent?, comment: String?) {
                    Log.d("Open", "Connection Open")
                }

                override fun onRetryTime(sse: ServerSentEvent?, milliseconds: Long): Boolean {
                    Log.d("Open", "Connection Open")
                    return true
                }

                override fun onRetryError(
                    sse: ServerSentEvent?,
                    throwable: Throwable?,
                    response: Response?
                ): Boolean {
                    Log.d("Open", "Connection Open")
                    return true
                }

                override fun onClosed(sse: ServerSentEvent?) {
                    Log.d("Open", "Connection Open")
                }

                // Change this function. I set the request to be null for demo purposes
                override fun onPreRetry(
                    sse: ServerSentEvent?,
                    originalRequest: Request?
                ): Request? {
                    var request: Request? = null
                    Log.d("Open", "Connection Open")
                    return request
                }

            })
        }
    }
}