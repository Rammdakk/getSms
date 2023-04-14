package com.rammdakk.getSms.data.net.datasource

import android.util.Log
import com.rammdakk.getSms.data.core.model.CountryInfo
import com.rammdakk.getSms.data.core.model.RentedNumber
import com.rammdakk.getSms.data.core.model.Service
import com.rammdakk.getSms.data.net.api.Result
import com.rammdakk.getSms.data.net.api.error.ErrorHandlerImpl
import com.rammdakk.getSms.data.net.api.error.InternetError
import com.rammdakk.getSms.data.net.api.exception.HttpException
import com.rammdakk.getSms.data.net.api.vakSms.VakSmsApi
import com.rammdakk.getSms.data.net.model.*
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.infra.castToInt
import com.rammdakk.getSms.ioc.ApplicationComponentScope
import org.jsoup.Jsoup
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@ApplicationComponentScope
class DataSource @Inject constructor(
    private val vakSmsApi: VakSmsApi,
) {
    suspend fun loadServices(country: String, cookie: String): Result<List<Service>, String> {
        try {
            val servicesResponse = vakSmsApi.getServices(country = country, cookie = cookie)
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

    suspend fun loadBalance(apiKey: String): Result<BalanceResponse, String> {
        return try {
            val balanceResponse = vakSmsApi.getBalance(apiKey)
            if (!balanceResponse.isSuccessful) {
                throw HttpException(balanceResponse.code(), "getServicesError")
            }
            if (balanceResponse.body() == null) {
                return Result.Error(InternetError.Unknown, "Не удалось получить значения")
            }
            Result.Success(balanceResponse.body()!!)
        } catch (ex: Exception) {
            Log.d("EXX", ex.toString())
            Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message)
        }
    }

    suspend fun loadCountries(cookie: String): Result<List<CountryInfo>, String> {
        return try {
            val countriesResponse = vakSmsApi.getCountries(cookie = cookie)
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

    suspend fun getNumber(
        apiKey: String,
        country: String,
        serviceID: String
    ): Result<NumberResponse, String> {
        return try {
            val numberResponse = vakSmsApi.getNumber(apiKey, serviceID, country)
            if (!numberResponse.isSuccessful) {
                Log.d("err", numberResponse.message())
                throw HttpException(numberResponse.code(), "getNumber")
            }
            if (numberResponse.body() == null) {
                return Result.Error(InternetError.Unknown, "Не удалось получить значения")
            }
            Result.Success(numberResponse.body()!!)
        } catch (ex: Exception) {
            Log.d("EXX", ex.toString())
            Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message)
        }
    }

    suspend fun setStatus(
        status: String,
        numberID: String,
        apiKey: String
    ): Result<StatusResponse, String> {
        return try {
            val statusResponse = vakSmsApi.setStatus(apiKey, status, numberID)
            if (!statusResponse.isSuccessful) {
                Log.d("err", statusResponse.message())
                throw HttpException(statusResponse.code(), "setStatus")
            }
            if (statusResponse.body() == null) {
                return Result.Error(InternetError.Unknown, "Не удалось обновить значения")
            }
            Result.Success(statusResponse.body()!!)
        } catch (ex: Exception) {
            Log.d("EXX", ex.toString())
            Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message)
        }
    }

    suspend fun loadNumbers(cookie: String): Result<List<RentedNumber>, String> =
        suspendCoroutine { continuation ->
            try {
                Log.d("Ramil--", cookie)
                val url =
                    "https://vak-sms.com/getNumber/"
                val doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .timeout(5000)
                    .header("cookie", cookie)
                    .referrer("http://google.com")
                    .header("Host", "vak-sms.com")
                    .get()
                val elements = doc.getElementsByClass("dropdown-toggle").map {
                    Jsoup.parse(it.html())
                }
                val res = elements.map { doc ->
                    val elements = doc.getElementsByAttributeValue("id", "copy")
                    val time = doc.getElementsByClass("countdown")
                    val codes = doc.getElementsByClass("codes")
                    RentedNumber(
                        serviceName = elements[0].text(),
                        numberId = elements[0].attr("rel"),
                        timeLeft = time[0].text().castToInt() ?: 0,
                        number = elements[1].attr("rel"),
                        codes = codes[0].text()
                    )
                }
                Log.d("Casted", res.toString())
                continuation.resume(Result.Success(res))
            } catch (ex: Exception) {
                Log.d("EXX", ex.toString())
                continuation.resume(Result.Error(ErrorHandlerImpl.getErrorType(ex), ex.message))
            }
        }
}