package com.rammdakk.getSms.data.api.exception

class HttpException(
    val code: Int,
    message: String,
    val body: String = ""
) : Exception(message) {
    override fun toString(): String {
        return "[$code, $message] ($body)"
    }
}
