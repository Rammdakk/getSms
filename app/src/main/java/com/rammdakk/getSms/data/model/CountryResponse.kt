package com.rammdakk.getSms.data.model

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("country")
    val country: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("operator")
    val operators: List<String>
)