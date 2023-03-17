package com.rammdakk.getSms.data.api

import com.rammdakk.getSms.data.model.ServiceInfoResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface JsonPlaceHolderApi {
    @GET("getCountNumberList/")
    suspend fun getServices(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String = "ru"
    ): Response<ResponseBody>
}