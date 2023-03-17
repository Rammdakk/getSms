package com.rammdakk.getSms.data.api

import com.rammdakk.getSms.ioc.ApplicationComponentScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object RetrofitModule {
    @ApplicationComponentScope
    @Provides
    fun jsonPlaceHolderApi(): JsonPlaceHolderApi {
        return Retrofit.Builder().baseUrl("https://vak-sms.com/api/").client(HttpClient.client)
            .build().create(JsonPlaceHolderApi::class.java)
    }
}