package com.rammdakk.getSms.data.api.error

internal interface ErrorHandler {
    fun getErrorType(throwable: Throwable): ErrorType
}