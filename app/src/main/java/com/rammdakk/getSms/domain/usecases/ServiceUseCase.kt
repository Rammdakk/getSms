package com.rammdakk.getSms.domain.usecases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rammdakk.getSms.SingleLiveEvent
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.data.repository.ServiceRepository
import com.rammdakk.getSms.data.api.Result
import com.rammdakk.getSms.data.model.CountryResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class ServiceUseCase @Inject constructor(private val servicesRepository: ServiceRepository) {

    private val _services = MediatorLiveData<List<Service>>()
    val services: LiveData<List<Service>> = _services

    private val _balance = MediatorLiveData<Double>()
    val balance: LiveData<Double> = _balance

    private val _error = MediatorLiveData<Result.Error<String>?>()
    val error: LiveData<Result.Error<String>?> = _error

    private val _countries = MediatorLiveData<List<CountryResponse>>()
    val countries: LiveData<List<CountryResponse>> = _countries

    private val combineCoroutineScope = CoroutineScope(Job() + Dispatchers.Default)


    init {
        _services.addSource(servicesRepository.services) {
            updateServicesLiveData()
        }
        _balance.addSource(servicesRepository.balance) {
            updateBalanceLiveData()
        }
        _error.addSource(servicesRepository.error) {
            updateErrorLiveData()
        }
        _countries.addSource(servicesRepository.countries) {
            updateCountriesLiveData()
        }
    }

    private fun updateServicesLiveData() {
        combineCoroutineScope.launch {
            _services.postValue(servicesRepository.services.value.orEmpty())
        }
    }

    private fun updateBalanceLiveData() {
        combineCoroutineScope.launch {
            _balance.postValue(servicesRepository.balance.value)
        }
    }

    private fun updateErrorLiveData() {
        combineCoroutineScope.launch {
            _error.postValue(servicesRepository.error.value)
        }
    }

    private fun updateCountriesLiveData() {
        combineCoroutineScope.launch {
            _countries.postValue(servicesRepository.countries.value)
        }
    }

    suspend fun loadServices(apiKey: String, country: String = "ru") {
        withContext(Dispatchers.IO) {
            servicesRepository.loadServices(apiKey, country)
        }
    }

    suspend fun updateBalance(apiKey: String) {
        withContext(Dispatchers.IO) {
            servicesRepository.updateBalance(apiKey)
        }
    }

    suspend fun loadCountries() {
        withContext(Dispatchers.IO) {
            servicesRepository.loadCountries()
        }
    }
}