package com.rammdakk.getSms.data.api.vakSms

import com.rammdakk.getSms.data.api.HttpClient
import com.rammdakk.getSms.ioc.ApplicationComponentScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object VakSmsRetrofitModule {
    @ApplicationComponentScope
    @Provides
    fun vakSmsApi(): VakSmsApi {
        return Retrofit.Builder().baseUrl("https://vak-sms.com/api/").addConverterFactory(
            GsonConverterFactory.create()
        ).client(HttpClient.client)
            .build().create(VakSmsApi::class.java)
    }
}

//@Module
//object VakSmsRetrofitModule2 {
//    @ApplicationComponentScope
//    @Provides
//    fun jsonPlaceHolderApi(): vakSmsApi {
//        return Retrofit.Builder().baseUrl("http://localhost:8080/").addConverterFactory(
//            GsonConverterFactory.create()
//        ).client(HttpClient.client)
//            .build().create(vakSmsApi::class.java)
//    }
//}