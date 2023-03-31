package com.rammdakk.getSms.data.model

import com.google.gson.annotations.SerializedName

data class NumberResponse(
    @SerializedName("tel")
    val number: String,
    @SerializedName("idNum")
    val numberID: String
)