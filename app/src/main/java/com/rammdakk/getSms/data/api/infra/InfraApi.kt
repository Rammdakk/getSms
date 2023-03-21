package com.rammdakk.getSms.data.api.infra

import com.rammdakk.getSms.data.model.CountryResponse
import retrofit2.Response
import retrofit2.http.GET

interface InfraApi {
    @GET("encode/countries")
    suspend fun getCountriesInfo(): Response<List<CountryResponse>>
}