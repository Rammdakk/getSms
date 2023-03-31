package com.rammdakk.getSms.data.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rammdakk.getSms.SingleLiveEvent
import com.rammdakk.getSms.core.model.Service
import com.rammdakk.getSms.data.api.Result
import com.rammdakk.getSms.data.api.error.InternetError
import com.rammdakk.getSms.data.datasource.DataSource
import com.rammdakk.getSms.data.model.CountryInfo
import com.rammdakk.getSms.data.model.NumberResponse
import com.rammdakk.getSms.data.model.StatusResponse
import com.rammdakk.getSms.ioc.ApplicationComponentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ApplicationComponentScope
class ServiceRepository @Inject constructor(
    private val dataSource: DataSource
) {

    private val _services = MutableLiveData<List<Service>>()
    val services = _services

    private val _balance = MutableLiveData<Double>()
    val balance = _balance

    private val _error = SingleLiveEvent<Result.Error<String>?>()
    val error = _error

    private val _countries = MutableLiveData<List<CountryInfo>>()
    val countries = _countries

    private val _number = SingleLiveEvent<NumberResponse>()
    val number = _number

    private val _status = MutableLiveData<StatusResponse>()
    val status = _status


    suspend fun loadServices(country: String) {
        try {
            val loadedList = withContext(Dispatchers.IO) {
                dataSource.loadServices(country)
            }
            when (loadedList) {
                is Result.Success -> {
                    _services.postValue(loadedList.data ?: emptyList())
                    _error.postValue(null)
                }
                is Result.Error -> {
                    _services.postValue(emptyList())
                    _error.postValue(loadedList)
                    Log.d("Loaded", loadedList.toString())
                }
            }

        } catch (e: Exception) {
            _services.postValue(emptyList())
            Log.d("exxRepo", e.toString())
            _error.postValue(Result.Error(InternetError.Unknown, e.message))
        }
    }

    suspend fun updateBalance(apiKey: String) {
        val balance = withContext(Dispatchers.IO) {
            dataSource.loadBalance(apiKey)
        }
        when (balance) {
            is Result.Success -> {
                _balance.postValue(balance.data ?: 0.00)
                _error.postValue(null)
            }
            is Result.Error -> {
                _balance.postValue(0.00)
                _error.postValue(balance)
                Log.d("Loaded", balance.toString())
            }
        }

    }

    suspend fun loadCountries() {
        val countries = withContext(Dispatchers.IO) {
            dataSource.loadCountries()
        }
        when (countries) {
            is Result.Success -> {
                _countries.postValue(countries.data ?: emptyList())
            }
            is Result.Error -> {
                _countries.postValue(emptyList())
                _error.postValue(countries)
                Log.d("Loaded", countries.toString())
            }
        }

    }

    suspend fun getNumber(apiKey: String, country: String, serviceID: String) {
        val number = withContext(Dispatchers.IO) {
            dataSource.getNumber(apiKey, country, serviceID)
        }
        when (number) {
            is Result.Success -> {
                _number.postValue(number.data!!)
            }
            is Result.Error -> {
                _error.postValue(number)
                Log.d("Loaded", countries.toString())
            }
        }
    }

    fun postError(error: InternetError, string: String) {
        _error.postValue(Result.Error(error, string))
    }

    suspend fun setStatus(status: String, numberID: String, apiKey: String) {
        val statusResponse = withContext(Dispatchers.IO) {
            dataSource.setStatus(status, numberID, apiKey)
        }
        when (statusResponse) {
            is Result.Success -> {
                _status.postValue(statusResponse.data!!)
                if (statusResponse.data.status != "ready" || statusResponse.data.status != "smsReceived") {
                    Log.d("ERR", statusResponse.data.status)
                    _error.postValue(
                        Result.Error(
                            InternetError.BadStatus,
                            statusResponse.data.status
                        )
                    )
                }
            }
            is Result.Error -> {
                _error.postValue(statusResponse)
                Log.d("Loaded", countries.toString())
            }
        }
    }

}
