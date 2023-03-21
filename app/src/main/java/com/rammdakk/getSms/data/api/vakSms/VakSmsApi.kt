package com.rammdakk.getSms.data.api.vakSms

import com.rammdakk.getSms.data.model.BalanceResponse
import com.rammdakk.getSms.data.model.ServiceInfoResponse
import retrofit2.Response
import retrofit2.http.*

interface VakSmsApi {
    @GET("getCountNumberList/")
    suspend fun getServices(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String = "ru"
    ): Response<Map<String, ServiceInfoResponse>>

    @GET("getBalance/")
    suspend fun getBalance(@Query("apiKey") apiKey: String): Response<BalanceResponse>
}