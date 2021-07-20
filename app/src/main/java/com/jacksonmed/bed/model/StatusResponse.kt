package com.jacksonmed.bed.model

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("status")
    val response: String
)
