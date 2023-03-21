package com.rammdakk.getSms.data.datasource

import android.util.Log
import com.rammdakk.getSms.data.api.Result
import com.rammdakk.getSms.data.api.error.ErrorHandlerImpl
import com.rammdakk.getSms.data.api.error.ErrorType
import com.rammdakk.getSms.data.api.exception.HttpException
import com.rammdakk.getSms.data.api.infra.InfraApi
import com.rammdakk.getSms.data.api.vakSms.VakSmsApi
import com.rammdakk.getSms.data.model.CountryResponse
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

    suspend fun loadServices(apiKey: String, country: String): Result<List<Service>, String> {
        try {
            val servicesResponse = vakSmsApi.getServices(apiKey, country)
            val serviceCodeResponse = infraApi.getServicesCode()
            if (!servicesResponse.isSuccessful) {
                throw HttpException(servicesResponse.code(), "getServicesError")
            }
            if (!serviceCodeResponse.isSuccessful) {
                throw HttpException(serviceCodeResponse.code(), "getServicesCodeError")
            }
            if (servicesResponse.body() == null || servicesResponse.body() == null) {
                return Result.Error(ErrorType.Unknown, "Не удалось получить значения")
            }
            val listOfTasks = servicesResponse.body()!!
            val map =
                serviceCodeResponse.body()!!.associateBy({ it.serviceID }, { it.serviceName })
            return Result.Success(listOfTasks.map { serviceInfoResponse ->
                Service(
                    serviceName = map.get(serviceInfoResponse.value.serviceID) ?: "undef",
                    serviceID = serviceInfoResponse.value.serviceID,
                    price = serviceInfoResponse.value.price,
                    imageUrl = "https://vak-sms.com/static/service/${serviceInfoResponse.value.serviceID}.png"
                )
            }.filter { it.serviceName != "undef" })
        } catch (ex: Exception) {
            return Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message)
        }
    }

    suspend fun loadBalance(apiKey: String): Result<Double, String> {
        return try {
            val balanceResponse = vakSmsApi.getBalance(apiKey)
            if (!balanceResponse.isSuccessful) {
                throw HttpException(balanceResponse.code(), "getServicesError")
            }
            if (balanceResponse.body() == null) {
                return Result.Error(ErrorType.Unknown, "Не удалось получить значения")
            }
            Result.Success(balanceResponse.body()!!.balance)
        } catch (ex: Exception) {
            Log.d("EXX", ex.toString())
            Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message)
        }
    }

    suspend fun loadCountries(): Result<List<CountryResponse>, String> {
        return try {
            val countriesResponse = infraApi.getCountriesInfo()
            if (!countriesResponse.isSuccessful) {
                throw HttpException(countriesResponse.code(), "getCountriesInfo")
            }
            if (countriesResponse.body() == null) {
                return Result.Error(ErrorType.Unknown, "Не удалось получить значения")
            }
            Result.Success(countriesResponse.body()!!)
        } catch (ex: Exception) {
            Log.d("EXX", ex.toString())
            Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message)
        }
    }

}