package com.rammdakk.getSms.data.api.vakSms

import com.rammdakk.getSms.data.model.BalanceResponse
import com.rammdakk.getSms.data.model.CountryResponse
import com.rammdakk.getSms.data.model.ServiceInfoResponse
import retrofit2.Response
import retrofit2.http.*

interface VakSmsApi {

    @GET("getBalance/")
    suspend fun getBalance(@Query("apiKey") apiKey: String): Response<BalanceResponse>

    @GET("getCountNumbersList/")
    suspend fun getServices(
        @Query("country") country: String = "ru"
    ): Response<Map<String, List<ServiceInfoResponse>>>

    @GET("getCountryOperatorList/")
    suspend fun getCountries(): Response<Map<String, List<CountryResponse>>>
}