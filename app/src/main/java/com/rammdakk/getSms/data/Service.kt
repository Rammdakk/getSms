package com.rammdakk.getSms.data

import android.graphics.drawable.Drawable
import java.util.*

data class Service(
    val ServiceName: String = "",
    val Price: Double = 0.0,
    val image: Drawable,
    val updatedDay: Date = Date(System.currentTimeMillis())

)