package com.rammdakk.getSms.data.model

import com.google.gson.annotations.SerializedName

data class NumberResponse(
    @SerializedName("tel")
    val number: String,
    @SerializedName("idNum")
    val numberID: String,
    @SerializedName("error")
    val error: Error?
)

enum class Error {
    @SerializedName("apiKeyNotFound")
    apiKeyNotFound,

    @SerializedName("noService")
    noService,

    @SerializedName("noNumber")
    noNumber,

    @SerializedName("noMoney")
    noMoney,

    @SerializedName("noCountry")
    noCountry,

    @SerializedName("noOperator")
    noOperator,

    @SerializedName("badStatus")
    badStatus,

    @SerializedName("idNumNotFound")
    idNumNotFound,

    @SerializedName("badService")
    badService,

    @SerializedName("badData")
    badData;

    override fun toString(): String {
        return when (this) {
            apiKeyNotFound -> "Неверный API ключ."
            noService -> "Данный сервис не поддерживается."
            noNumber -> "Нет номеров, попробуйте позже."
            noMoney -> "Недостаточно средств, пополните баланс."
            noCountry -> "Запрашиваемая страна отсутствует."
            noOperator -> "Оператор не найден для запрашиваемой страны."
            badStatus -> "Не верный статус."
            idNumNotFound -> "Не верный ID операции."
            badService -> "Не верный код сайта, сервиса, соц. сети."
            badData -> "Отправлены неверные данные."
        }
    }
}