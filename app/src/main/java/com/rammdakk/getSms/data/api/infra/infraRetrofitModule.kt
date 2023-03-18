package com.rammdakk.getSms.data.api.infra

import com.rammdakk.getSms.data.api.HttpClient
import com.rammdakk.getSms.ioc.ApplicationComponentScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class InfraRetrofitModule {
    @ApplicationComponentScope
    @Provides
    fun infraApi(): InfraApi {
        return Retrofit.Builder().baseUrl("http://10.0.2.2:8080/").addConverterFactory(
            GsonConverterFactory.create()
        ).client(HttpClient.client)
            .build().create(InfraApi::class.java)
    }
}
