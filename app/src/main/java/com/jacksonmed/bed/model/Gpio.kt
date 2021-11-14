package com.jacksonmed.bed.model

import com.google.gson.annotations.SerializedName

data class GpioTest(
    @SerializedName("gpio_pin") val pin: Int,
    @SerializedName("state") val state: Int
)
