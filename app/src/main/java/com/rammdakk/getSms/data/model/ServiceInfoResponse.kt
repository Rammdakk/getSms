package com.rammdakk.getSms.data.model

import com.google.gson.annotations.SerializedName

data class ServiceInfoResponse(
    @SerializedName("code")
    val serviceID: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("price")
    val price: Double
)
