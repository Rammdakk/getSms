package com.rammdakk.getSms.data.datasource

import android.util.Log
import com.rammdakk.getSms.data.api.JsonPlaceHolderApi
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.data.model.ServiceInfoResponse
import com.rammdakk.getSms.ioc.ApplicationComponentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import javax.inject.Inject


@ApplicationComponentScope
class DataSource @Inject constructor(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi
) {

    val REGEX = "\"[a-z]{1,10}\": \\{".toRegex()

    fun editString(string: String?): String {
        if (string == null) return "[]"
        val str = string.replace(REGEX, "{").substring(1)
        return "[${str.substring(str.indexOf("{"), str.length - 1)}]"
    }

    suspend fun loadServices(): List<Service> {
        val response = jsonPlaceHolderApi.getServices("0ee01dabbb854b4d9155ef1ba9a4f652")
        Log.d("resp", response.body().toString())
        var listOfTasks = emptyList<ServiceInfoResponse>()
        withContext(Dispatchers.Main) {
            try {
                if (response.isSuccessful) {
                    val string = editString(response.body()?.string())
                    val json = Json
                    listOfTasks = json.decodeFromString(string)
                } else {
                    Log.d("response.isNotSuccessful", response.message())
                }
            } catch (e: HttpException) {
                // Toast.makeText("Exception ${e.message}")
            }
        }
        return listOfTasks.map { serviceInfoResponse ->
            Service(
                serviceName = serviceInfoResponse.serviceID,
                price = serviceInfoResponse.price,
                imageUrl = ""
            )
        }
    }

}