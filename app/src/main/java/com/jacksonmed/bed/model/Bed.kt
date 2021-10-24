package com.jacksonmed.bed.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bed(
    @SerialName("gpio_pins")
    val gpioPins: Array<Gpio>,
)

@Serializable
data class Gpio(
    @SerialName("gpio_pin")
    val gpioPin: Int,
    val state: Int,
)
