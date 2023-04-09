package com.rammdakk.getSms.data.net.model

import com.google.gson.annotations.SerializedName

data class ServiceInfoResponse(
    @SerializedName("name")
    val serviceName: String,
    @SerializedName("icon")
    val imageUrl: String,
    @SerializedName("quantity")
    val count: Int,
    @SerializedName("cost")
    val price: Double,
)
