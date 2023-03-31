package com.rammdakk.getSms.core.model

import com.google.gson.annotations.SerializedName

data class RentedNumber(
    @SerializedName("name")
    val serviceName: String = "",
    @SerializedName("id")
    val numberId: String,
    @SerializedName("time")
    val timeLeft: Int = 0,
    @SerializedName("number")
    val number: String,
    @SerializedName("code")
    val codes: String
)
