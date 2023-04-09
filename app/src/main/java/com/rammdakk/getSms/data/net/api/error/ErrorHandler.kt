package com.rammdakk.getSms.data.net.api.error

internal interface ErrorHandler {
    fun getErrorType(throwable: Throwable): InternetError
}