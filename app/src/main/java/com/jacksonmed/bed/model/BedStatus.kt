package com.jacksonmed.bed.model

import com.google.gson.annotations.SerializedName

data class BedStatus(@SerializedName("gpio_pins") val gpioPins: Array<GpioTest>)
