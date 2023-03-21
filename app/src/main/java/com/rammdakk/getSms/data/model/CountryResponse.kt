package com.rammdakk.getSms.data.model

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("name")
    val country: String,
    @SerializedName("icon")
    val imageUrl: String
)