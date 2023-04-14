package com.rammdakk.getSms.ioc

interface ResultHandler<T> {
    fun onSuccess(data: T)
    fun onError(message: String)
}
