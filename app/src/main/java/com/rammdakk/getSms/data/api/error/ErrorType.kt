package com.rammdakk.getSms.data.api.error

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Keep
sealed class ErrorType : Parcelable {
    @Parcelize
    @Keep
    object Network : ErrorType() {
        override fun toString(): String {
            return "network unreachable"
        }
    }

    @Parcelize
    @Keep
    object Timeout : ErrorType() {
        override fun toString(): String {
            return "connection timeout is reached"
        }
    }

    @Parcelize
    @Keep
    object BadRequest : ErrorType()
    @Parcelize
    @Keep
    object NotFound : ErrorType()
    @Parcelize
    @Keep
    object AccessDenied : ErrorType()
    @Parcelize
    @Keep
    object Unauthorized : ErrorType()
    @Parcelize
    @Keep
    object ServiceUnavailable : ErrorType()
    @Parcelize
    @Keep
    object Unknown : ErrorType()
    @Parcelize
    @Keep
    object ServerError : ErrorType()
    @Parcelize
    @Keep
    object TooManyRequests : ErrorType()
    @Parcelize
    @Keep
    object ProtocolException : ErrorType()

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}


fun ErrorType.TooManyRequests.parseDateTime(errMessage: String): Date? {
    val regex = Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")
    val parsedDateTime = regex.find(errMessage)?.groupValues?.get(0) ?: return null
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    return try {
        formatter.parse(parsedDateTime)
    } catch (e: ParseException) {
        null
    }
}
