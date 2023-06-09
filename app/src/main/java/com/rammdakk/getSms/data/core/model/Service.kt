package com.rammdakk.getSms.data.core.model

data class Service(
    val serviceName: String = "",
    val serviceID: String,
    val price: Double = 0.0,
    val quantity: Int,
    val imageUrl: String
)