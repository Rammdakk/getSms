package com.rammdakk.getSms.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object HttpClient {

    private fun getLogs(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return interceptor
    }


    val client: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(getLogs()).build()

}


