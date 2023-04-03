package com.rammdakk.getSms.data.api.error

import android.content.res.Resources
import com.rammdakk.getSms.R

internal interface ErrorMessageConverter {
    fun getError(internetError: InternetError, message: Any): String
}

internal class ErrorMessageConverterImpl(
    private val resources: Resources,
) : ErrorMessageConverter {

    override fun getError(internetError: InternetError, message: Any): String =
        when (internetError) {
            is InternetError.Network -> resources.getString(R.string.error_network)
            is InternetError.Timeout -> resources.getString(R.string.error_network_timeout)
            is InternetError.BadRequest -> resources.getString(R.string.error_network_bad_request)
            is InternetError.NotFound -> resources.getString(R.string.error_network_not_found)
            is InternetError.AccessDenied -> resources.getString(R.string.error_network_access_denied)
            is InternetError.Unauthorized -> resources.getString(R.string.error_network_unauthorized)
            is InternetError.ServiceUnavailable -> resources.getString(R.string.error_network_service_unavailable)
            is InternetError.Unknown -> resources.getString(
                R.string.error_unknown,
                message.toString().replace("\n", " ")
            )
            is InternetError.ServerInternetError -> resources.getString(R.string.error_50x)
            is InternetError.TooManyRequests -> resources.getString(R.string.error_too_many_requests)
            is InternetError.ProtocolException -> resources.getString(
                R.string.error_protocol_exception
            )
            is InternetError.NoData -> resources.getString(
                R.string.no_data
            )
            is InternetError.BadStatus -> resources.getString(R.string.bad_status)
            is InternetError.Default -> resources.getString(R.string.default_error, message)
        }
}
