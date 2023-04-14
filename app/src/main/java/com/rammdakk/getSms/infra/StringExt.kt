package com.rammdakk.getSms.infra

fun String.castToInt(): Int? {
    return try {
        this.toInt()
    } catch (ex: Exception) {
        null
    }
}
