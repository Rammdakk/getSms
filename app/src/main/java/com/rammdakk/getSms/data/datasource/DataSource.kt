package com.rammdakk.getSms.data.datasource

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rammdakk.getSms.data.api.JsonPlaceHolderApi
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.data.model.ServiceIDResponse
import com.rammdakk.getSms.data.model.ServiceInfoResponse
import com.rammdakk.getSms.ioc.ApplicationComponentScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import javax.inject.Inject


@ApplicationComponentScope
class DataSource @Inject constructor(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi
) {

    val regex = "\"\\w{1,10}\": \\{".toRegex()

    private fun editString(string: String?): String {
        if (string == null) return "[]"
        val str = string.replace(regex, "{").substring(1)
        return "[${str.substring(str.indexOf("{"), str.length - 1)}]"
    }

    suspend fun loadServices(): List<Service> {
        val response = jsonPlaceHolderApi.getServices("0ee01dabbb854b4d9155ef1ba9a4f652")
        val string = editString(response.body()?.string())
        val type = object : TypeToken<List<ServiceInfoResponse>>() {}.type
        val listOfTasks: List<ServiceInfoResponse> = Gson().fromJson(string, type)
        return listOfTasks.map { serviceInfoResponse ->
            Service(
                serviceName = serviceInfoResponse.serviceID,
                price = serviceInfoResponse.price,
                imageUrl = ""
            )
        }
    }

}