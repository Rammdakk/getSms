package com.rammdakk.getSms.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rammdakk.getSms.data.core.model.CountryInfo
import com.rammdakk.getSms.data.core.model.Service
import com.rammdakk.getSms.data.net.api.error.InternetError
import com.rammdakk.getSms.data.net.repository.ServiceRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class ServiceUseCase @Inject constructor(private val servicesRepository: ServiceRepository) {

    private val _services = MediatorLiveData<List<Service>>()
    val services: LiveData<List<Service>> = _services

    private val _balance = MediatorLiveData<Double>()
    val balance: LiveData<Double> = _balance

    private val _countries = MediatorLiveData<List<CountryInfo>>()
    val countries: LiveData<List<CountryInfo>> = _countries

    private val combineCoroutineScope = CoroutineScope(Job() + Dispatchers.Default)


    init {
        _services.addSource(servicesRepository.services) {
            updateServicesLiveData()
        }
        _balance.addSource(servicesRepository.balance) {
            updateBalanceLiveData()
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

    private fun updateCountriesLiveData() {
        combineCoroutineScope.launch {
            _countries.postValue(servicesRepository.countries.value)
        }
    }

    suspend fun loadServices(country: String = "ru", cookie: String) {
        withContext(Dispatchers.IO) {
            servicesRepository.loadServices(country, cookie)
        }
    }

    suspend fun updateBalance(apiKey: String) {
        withContext(Dispatchers.IO) {
            servicesRepository.updateBalance(apiKey)
        }
    }

    suspend fun loadCountries(cookie: String) {
        withContext(Dispatchers.IO) {
            servicesRepository.loadCountries(cookie)
        }
    }

    suspend fun postError(error: InternetError, string: String = "") {
        withContext(Dispatchers.Default) {
            servicesRepository.postError(error, string)
        }
    }
}