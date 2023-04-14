package com.rammdakk.getSms.data.net.api.error

import com.google.gson.JsonSyntaxException
import com.rammdakk.getSms.data.net.api.exception.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.ProtocolException
import java.net.SocketTimeoutException

private const val HTTP_TOO_MANY_REQUESTS = 429
private const val SERVER_ERROR_CODE_START = 500
private const val SERVER_ERROR_CODE_END = 599

internal interface ErrorHandler {
    fun getErrorType(throwable: Throwable): InternetError
}

internal object ErrorHandlerImpl : ErrorHandler {
    override fun getErrorType(throwable: Throwable): InternetError {
        return when (throwable) {
            is ProtocolException -> InternetError.ProtocolException
            is SocketTimeoutException -> InternetError.Timeout
            is IOException -> InternetError.Network
            is HttpException -> {
                when (throwable.code) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> InternetError.BadRequest
                    HttpURLConnection.HTTP_NOT_FOUND -> InternetError.NotFound
                    HttpURLConnection.HTTP_UNAUTHORIZED -> InternetError.Unauthorized
                    HttpURLConnection.HTTP_FORBIDDEN -> InternetError.AccessDenied
                    HttpURLConnection.HTTP_UNAVAILABLE -> InternetError.ServiceUnavailable
                    HTTP_TOO_MANY_REQUESTS -> InternetError.TooManyRequests
                    in SERVER_ERROR_CODE_START..SERVER_ERROR_CODE_END -> InternetError.ServerInternetError
                    else -> InternetError.Unknown
                }
            }
            is JsonSyntaxException -> InternetError.NoData
            else -> InternetError.Unknown
        }
    }
}