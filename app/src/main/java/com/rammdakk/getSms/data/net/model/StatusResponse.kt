package com.rammdakk.getSms.data.net.model

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("status")
    val status: String
)
