package com.rammdakk.getSms.data.api.infra

import com.rammdakk.getSms.data.model.ServiceIDResponse
import retrofit2.Response
import retrofit2.http.GET

interface InfraApi {

//    @GET("encode/services")
//    suspend fun getServicesCode(): Response<Map<String, String>>

    @GET("encode/services")
    suspend fun getServicesCode(): Response<List<ServiceIDResponse>>
}