package com.rammdakk.getSms.data.datasource

import android.util.Log
import com.rammdakk.getSms.core.model.Service
import com.rammdakk.getSms.data.api.Result
import com.rammdakk.getSms.data.api.error.ErrorHandlerImpl
import com.rammdakk.getSms.data.api.error.InternetError
import com.rammdakk.getSms.data.api.exception.HttpException
import com.rammdakk.getSms.data.api.infra.InfraApi
import com.rammdakk.getSms.data.api.vakSms.VakSmsApi
import com.rammdakk.getSms.data.model.CountryInfo
import com.rammdakk.getSms.data.model.CountryResponse
import com.rammdakk.getSms.data.model.ServiceInfoResponse
import com.rammdakk.getSms.infra.UrlLinks
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

    suspend fun loadServices(country: String): Result<List<Service>, String> {
        try {
            val servicesResponse = vakSmsApi.getServices(country)
            if (!servicesResponse.isSuccessful) {
                throw HttpException(servicesResponse.code(), "getServicesError")
            }
            if (servicesResponse.body() == null || servicesResponse.body() == null) {
                return Result.Error(InternetError.Unknown, "Не удалось получить значения")
            }
            val listOfTasks = servicesResponse.body()!!
            return Result.Success(listOfTasks.map { convertToService(it) })
        } catch (ex: Exception) {
            return Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message)
        }
    }

    private fun convertToService(serviceInfoResponse: Map.Entry<String, List<ServiceInfoResponse>>): Service {
        val service = serviceInfoResponse.value[0]
        return Service(
            serviceName = service.serviceName,
            serviceID = serviceInfoResponse.key,
            price = service.price,
            imageUrl = UrlLinks.URL_BASE + service.imageUrl,
            quantity = service.count
        )
    }

    suspend fun loadBalance(apiKey: String): Result<Double, String> {
        return try {
            val balanceResponse = vakSmsApi.getBalance(apiKey)
            if (!balanceResponse.isSuccessful) {
                throw HttpException(balanceResponse.code(), "getServicesError")
            }
            if (balanceResponse.body() == null) {
                return Result.Error(InternetError.Unknown, "Не удалось получить значения")
            }
            Result.Success(balanceResponse.body()!!.balance)
        } catch (ex: Exception) {
            Log.d("EXX", ex.toString())
            Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message)
        }
    }

    suspend fun loadCountries(): Result<List<CountryInfo>, String> {
        return try {
            val countriesResponse = vakSmsApi.getCountries()
            if (!countriesResponse.isSuccessful) {
                throw HttpException(countriesResponse.code(), "getCountriesInfo")
            }
            if (countriesResponse.body() == null) {
                return Result.Error(InternetError.Unknown, "Не удалось получить значения")
            }

            Result.Success(countriesResponse.body()!!.map { convertToCountries(it) })
        } catch (ex: Exception) {
            Log.d("EXX", ex.toString())
            Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message)
        }
    }

    private fun convertToCountries(countryResponse: Map.Entry<String, List<CountryResponse>>): CountryInfo {
        val country = countryResponse.value[0]
        return CountryInfo(
            countryCode = countryResponse.key.lowercase(),
            country = country.country,
            imageUrl = UrlLinks.URL_BASE + country.imageUrl
        )
    }
}