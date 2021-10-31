package com.jacksonmed.bed.utils

import android.util.Log
import com.here.oksse.ServerSentEvent
import okhttp3.Request

class SseListener: ServerSentEvent.Listener {
    override fun onOpen(sse: ServerSentEvent?, response: okhttp3.Response?) {
        Log.d("Open", "Connection Open")
    }

    override fun onMessage(
        sse: ServerSentEvent?,
        id: String?,
        event: String?,
        message: String?
    ) {
        //Create Response and update live data
        if (event == "newframe"){
//                        calculateBedBitMap(message)
        }

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
        response: okhttp3.Response?
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

}
