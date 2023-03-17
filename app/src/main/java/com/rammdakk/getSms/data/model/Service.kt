package com.rammdakk.getSms.data.model

import java.util.*

data class Service(
    val serviceName: String = "",
    val price: Double = 0.0,
    val imageUrl: String,
    val updatedDay: Date = Date(System.currentTimeMillis())
)