package com.rammdakk.getSms.data.net.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rammdakk.getSms.data.core.model.CountryInfo
import com.rammdakk.getSms.data.core.model.RentedNumber
import com.rammdakk.getSms.data.core.model.Service
import com.rammdakk.getSms.data.net.api.Result
import com.rammdakk.getSms.data.net.api.error.InternetError
import com.rammdakk.getSms.data.net.datasource.DataSource
import com.rammdakk.getSms.data.net.model.NumberResponse
import com.rammdakk.getSms.data.net.model.StatusResponse
import com.rammdakk.getSms.infra.SingleLiveEvent
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

    private val _error =
        SingleLiveEvent<Result.Error<String>?>()
    val error = _error

    private val _countries = MutableLiveData<List<CountryInfo>>()
    val countries = _countries

    private val _rentedNumbers = MutableLiveData<List<RentedNumber>>()
    val rentedNumbers = _rentedNumbers

    private val _number =
        SingleLiveEvent<NumberResponse>()
    val number = _number

    private val _status = MutableLiveData<StatusResponse>()
    val status = _status


    suspend fun loadServices(country: String, cookie: String) {
        try {
            val loadedList = withContext(Dispatchers.IO) {
                dataSource.loadServices(country, cookie)
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
                if (balance.data.error != null) {
                    _error.postValue(
                        Result.Error(
                            InternetError.Default,
                            balance.data.error.toString()
                        )
                    )
                } else {
                    _balance.postValue(balance.data.balance)
                    _error.postValue(null)
                }
            }
            is Result.Error -> {
                _balance.postValue(0.00)
                _error.postValue(balance)
                Log.d("Loaded", balance.toString())
            }
        }

    }

    suspend fun loadCountries(cookie: String) {
        val countries = withContext(Dispatchers.IO) {
            dataSource.loadCountries(cookie)
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
                if (number.data.error != null) {
                    _error.postValue(
                        Result.Error(
                            InternetError.Default,
                            number.data.error.toString()
                        )
                    )
                } else {
                    _number.postValue(number.data!!)
                }
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
                if (statusResponse.data.status != "ready" && statusResponse.data.status != "update") {
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

    suspend fun getNumbers(cookie: String) {
        val statusResponse = withContext(Dispatchers.IO) {
            dataSource.loadNumbers(cookie)
        }
        when (statusResponse) {
            is Result.Success -> {
                _rentedNumbers.postValue(statusResponse.data!!)
            }
            is Result.Error -> {
                _error.postValue(statusResponse)
            }
        }
    }

}
