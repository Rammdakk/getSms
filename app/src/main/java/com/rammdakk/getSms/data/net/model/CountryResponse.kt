package com.rammdakk.getSms.data.net.model

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("name")
    val country: String,
    @SerializedName("icon")
    val imageUrl: String
)