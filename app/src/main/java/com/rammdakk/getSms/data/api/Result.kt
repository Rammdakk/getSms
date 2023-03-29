package com.rammdakk.getSms.data.api

import com.rammdakk.getSms.data.api.error.InternetError

sealed class Result<out T, out E> {
    class Success<T>(
        val data: T
    ) : Result<T, Nothing>() {
        override fun toString(): String {
            return "[Result.Success] ($data)"
        }
    }

    class Error<E>(
        val type: InternetError,
        val details: E? = null
    ) : Result<Nothing, E>() {
        override fun toString(): String {
            val items = listOfNotNull(type, details).joinToString(", ")
            return "[Result.Error] ($items)"
        }
    }
}
