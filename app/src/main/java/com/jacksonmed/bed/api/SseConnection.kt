package com.jacksonmed.bed.api

import android.util.Log
import com.here.oksse.OkSse
import com.here.oksse.ServerSentEvent
import com.jacksonmed.bed.model.StatusResponse
import com.jacksonmed.bed.utils.Constants
import okhttp3.Request
import okhttp3.Response

class SseConnection(url: String) {
    private lateinit var sse: ServerSentEvent
    private lateinit var request: Request
    private lateinit var okSse: OkSse

    init {
        request = Request.Builder().url(url).build()
        okSse = OkSse()
    }

    fun runSse() {
        sse = okSse.newServerSentEvent(request, object: ServerSentEvent.Listener {
            override fun onOpen(sse: ServerSentEvent?, response: Response?) {
                Log.d("Open", "Connection Open")
            }

            override fun onMessage(
                sse: ServerSentEvent?,
                id: String?,
                event: String?,
                message: String?
            ) {
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
            override fun onPreRetry(sse: ServerSentEvent?, originalRequest: Request?): Request? {
                var request: Request? = null
                Log.d("Open", "Connection Open")
                return request
            }

        })
    }
}