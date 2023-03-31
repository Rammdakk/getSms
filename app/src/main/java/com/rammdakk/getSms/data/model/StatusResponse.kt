package com.rammdakk.getSms.data.model

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("status")
    val status: String
)
