package com.rammdakk.getSms.data.net.model

import com.google.gson.annotations.SerializedName

data class ServiceIDResponse(
    @SerializedName("name")
    val serviceName: String,
    @SerializedName("service_id")
    val serviceID:String
)
