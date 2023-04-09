package com.rammdakk.getSms.data.net.model

import com.google.gson.annotations.SerializedName

data class OperatorsResponse(
    @SerializedName("country_code")
    var countryCode: String,
    @SerializedName("operator")
    var operators: String
)