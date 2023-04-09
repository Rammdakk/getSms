package com.rammdakk.getSms.data.net.model

import com.google.gson.annotations.SerializedName

data class BalanceResponse(
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("error")
    val error: Error?
)
