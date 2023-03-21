package com.rammdakk.getSms.data.api.error

import android.content.res.Resources
import com.rammdakk.getSms.R

internal interface ErrorMessageConverter {
    fun getError(errorType: ErrorType, message: Any): String
    fun getErrors(errorType: ErrorType, message: Any): List<String>
}

internal class ErrorMessageConverterImpl(
    private val resources: Resources,
) : ErrorMessageConverter {

    override fun getError(errorType: ErrorType, message: Any): String = when (errorType) {
        is ErrorType.Network -> resources.getString(R.string.error_network)
        is ErrorType.Timeout -> resources.getString(R.string.error_network_timeout)
        is ErrorType.BadRequest -> resources.getString(R.string.error_network_bad_request)
        is ErrorType.NotFound -> resources.getString(R.string.error_network_not_found)
        is ErrorType.AccessDenied -> resources.getString(R.string.error_network_access_denied)
        is ErrorType.Unauthorized -> resources.getString(R.string.error_network_unauthorized)
        is ErrorType.ServiceUnavailable -> resources.getString(R.string.error_network_service_unavailable)
        is ErrorType.Unknown -> resources.getString(
            R.string.error_unknown,
            message.toString().replace("\n", " ")
        )
        is ErrorType.ServerError -> resources.getString(R.string.error_50x)
        is ErrorType.TooManyRequests -> resources.getString(R.string.error_too_many_requests)
        is ErrorType.ProtocolException -> resources.getString(
            R.string.error_protocol_exception
        )
        is ErrorType.NoData -> resources.getString(
            R.string.no_data
        )
    }

    override fun getErrors(errorType: ErrorType, message: Any): List<String> {
        return getError(errorType, message).split("\n")
    }
}
