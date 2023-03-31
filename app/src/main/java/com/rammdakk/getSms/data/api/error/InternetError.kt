package com.rammdakk.getSms.data.api.error

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
sealed class InternetError : Parcelable {
    @Parcelize
    @Keep
    object Network : InternetError()

    @Parcelize
    @Keep
    object Timeout : InternetError()

    @Parcelize
    @Keep
    object BadRequest : InternetError()

    @Parcelize
    @Keep
    object NotFound : InternetError()

    @Parcelize
    @Keep
    object AccessDenied : InternetError()

    @Parcelize
    @Keep
    object Unauthorized : InternetError()

    @Parcelize
    @Keep
    object ServiceUnavailable : InternetError()

    @Parcelize
    @Keep
    object Unknown : InternetError()

    @Parcelize
    @Keep
    object ServerInternetError : InternetError()

    @Parcelize
    @Keep
    object TooManyRequests : InternetError()

    @Parcelize
    @Keep
    object ProtocolException : InternetError()

    @Parcelize
    @Keep
    object NoData : InternetError()

    @Parcelize
    @Keep
    object BadStatus : InternetError()

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}


