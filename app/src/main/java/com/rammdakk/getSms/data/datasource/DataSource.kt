package com.rammdakk.getSms.data.datasource

import android.util.Log
import com.rammdakk.getSms.data.api.infra.InfraApi
import com.rammdakk.getSms.data.api.vakSms.VakSmsApi
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.ioc.ApplicationComponentScope
import javax.inject.Inject


@ApplicationComponentScope
class DataSource @Inject constructor(
    private val vakSmsApi: VakSmsApi,
    private val infraApi: InfraApi
) {

    val regex = "\"\\w{1,10}\": \\{".toRegex()

    private fun editString(string: String?): String {
        if (string == null) return "[]"
        val str = string.replace(regex, "{").substring(1)
        return "[${str.substring(str.indexOf("{"), str.length - 1)}]"
    }

    suspend fun loadServices(): List<Service> {
        val response = vakSmsApi.getServices("0ee01dabbb854b4d9155ef1ba9a4f652")
        Log.d("resp", response.body()?.entries.toString())
        val listOfTasks = response.body()
        val response2 = infraApi.getServicesCode()
        var map = response2.body()?.associateBy({ it.serviceID }, { it.serviceName })
        if (map == null) {
            map = HashMap()
        }
        if (listOfTasks != null) {
            return listOfTasks.map { serviceInfoResponse ->
                Service(
                    serviceName = map.get(serviceInfoResponse.value.serviceID) ?: "undef",
                    serviceID = serviceInfoResponse.value.serviceID,
                    price = serviceInfoResponse.value.price,
                    imageUrl = "https://vak-sms.com/static/service/${serviceInfoResponse.value.serviceID}.png"
                )
            }.filter { it.serviceName != "undef" }
        } else {
            return emptyList()
        }
    }

}