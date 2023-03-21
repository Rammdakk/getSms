package com.rammdakk.getSms.data.model

import com.google.gson.annotations.SerializedName

data class BalanceResponse(
    @SerializedName("balance")
    val balance: Double
)
