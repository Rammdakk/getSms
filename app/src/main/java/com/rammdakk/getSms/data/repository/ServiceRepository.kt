package com.rammdakk.getSms.data.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rammdakk.getSms.data.api.Result
import com.rammdakk.getSms.data.api.error.ErrorType
import com.rammdakk.getSms.data.datasource.DataSource
import com.rammdakk.getSms.data.model.CountryResponse
import com.rammdakk.getSms.data.model.Service
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

    private val _error = MutableLiveData<Result.Error<String>?>()
    val error = _error

    private val _countries = MutableLiveData<List<CountryResponse>>()
    val countries = _countries


    suspend fun loadServices(apiKey: String, country: String) {
        try {
            val loadedList = withContext(Dispatchers.IO) {
                dataSource.loadServices(apiKey, country)
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
            _error.postValue(Result.Error(ErrorType.Unknown, e.message))
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
            else -> {
                _balance.postValue(0.00)
                Log.d("Loaded", balance.toString())
            }
        }

    }

    suspend fun loadCountries() {
        val balance = withContext(Dispatchers.IO) {
            dataSource.loadCountries()
        }
        when (balance) {
            is Result.Success -> {
                _countries.postValue(balance.data ?: emptyList())
            }
            else -> {
                _countries.postValue(emptyList())
                Log.d("Loaded", balance.toString())
            }
        }

    }

}
