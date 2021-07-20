package com.jacksonmed.bed.model

import com.google.gson.annotations.SerializedName

data class Bed(
    @SerializedName("gpio_pins")
    val gpioPins: Array<Gpio>,
)


data class Gpio(
    @SerializedName("gpio_pin")
    val gpioPin: Int,
    val state: Int,
)
