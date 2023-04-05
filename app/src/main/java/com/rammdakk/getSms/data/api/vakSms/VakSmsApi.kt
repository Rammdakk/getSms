package com.rammdakk.getSms.data.api.vakSms

import com.rammdakk.getSms.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface VakSmsApi {

    companion object {
        const val HOST = "vak-sms.com"
        const val REFERER = "https://vak-sms.com/"
    }

    @GET("getBalance/")
    suspend fun getBalance(@Query("apiKey") apiKey: String): Response<BalanceResponse>

    @GET("getCountNumbersList/")
    suspend fun getServices(
        @Header("Host") host: String = HOST,
        @Header("Cookie") cookie: String,
        @Header("Referer") referer: String = REFERER,
        @Query("country") country: String = "ru"
    ): Response<Map<String, List<ServiceInfoResponse>>>

    @GET("getCountryOperatorList/")
    suspend fun getCountries(
        @Header("Host") host: String = HOST,
        @Header("Cookie") cookie: String,
        @Header("Referer") referer: String = REFERER,
    ): Response<Map<String, List<CountryResponse>>>

    @GET("getNumber/")
    suspend fun getNumber(
        @Query("apiKey") apiKey: String,
        @Query("service") service: String,
        @Query("country") country: String = "ru",
    ): Response<NumberResponse>

    @GET("setStatus/")
    suspend fun setStatus(
        @Query("apiKey") apiKey: String,
        @Query("status") status: String,
        @Query("idNum") numberID: String = "ru",
    ): Response<StatusResponse>
}