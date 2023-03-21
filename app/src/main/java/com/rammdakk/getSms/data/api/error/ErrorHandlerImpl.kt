package com.rammdakk.getSms.data.api.error

import com.rammdakk.getSms.data.api.exception.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.ProtocolException
import java.net.SocketTimeoutException

private const val HTTP_TOO_MANY_REQUESTS = 429
private const val SERVER_ERROR_CODE_START = 500
private const val SERVER_ERROR_CODE_END = 599

internal object ErrorHandlerImpl : ErrorHandler {
    override fun getErrorType(throwable: Throwable): ErrorType {
        return when (throwable) {
            is ProtocolException -> ErrorType.ProtocolException
            is SocketTimeoutException -> ErrorType.Timeout
            is IOException -> ErrorType.Network
            is HttpException -> {
                when (throwable.code) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> ErrorType.BadRequest
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorType.NotFound
                    HttpURLConnection.HTTP_UNAUTHORIZED -> ErrorType.Unauthorized
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorType.AccessDenied
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorType.ServiceUnavailable
                    HTTP_TOO_MANY_REQUESTS -> ErrorType.TooManyRequests
                    in SERVER_ERROR_CODE_START..SERVER_ERROR_CODE_END -> ErrorType.ServerError
                    else -> ErrorType.Unknown
                }
            }
            else -> ErrorType.Unknown
        }
    }
}