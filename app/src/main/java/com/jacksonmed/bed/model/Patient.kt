package com.jacksonmed.bed.model

import com.google.gson.annotations.SerializedName

data class Patient(
    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    val age: Int,

    val height: Int,

    val weight: Int
)
