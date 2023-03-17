package com.rammdakk.getSms.data.model

import com.google.gson.annotations.SerializedName

data class ServiceIDResponse(
    @SerializedName("name")
    val serviceName: String,
    @SerializedName("service_id")
    val serviceID:String
)
