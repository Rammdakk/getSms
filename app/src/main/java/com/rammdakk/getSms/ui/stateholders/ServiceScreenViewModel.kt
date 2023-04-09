package com.rammdakk.getSms.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.getSms.connectvity.ConnectivityObserver
import com.rammdakk.getSms.data.net.api.error.InternetError
import com.rammdakk.getSms.domain.usecases.RentedNumberUseCase
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ServiceScreenViewModel(
    private val serviceUseCase: ServiceUseCase,
    private val rentedNumberUseCase: RentedNumberUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    val services = serviceUseCase.services
    val countries = serviceUseCase.countries
    val numberReference = rentedNumberUseCase.number

    private var apiKey = ""
    private var country = "ru"
    private var cookie = ""

    fun configure(key: String, cookie: String) {
        apiKey = key
        this.cookie = cookie
        updateBalance()
        updateCountries()
        connectivityObserver.observe().onEach {
            if (it == ConnectivityObserver.Status.Available) {
                if ((countries.value?.size ?: 0) < 1) {
                    updateCountries()
                } else {
                    updateServices()
                }
                serviceUseCase.updateBalance(apiKey)
            } else {
                serviceUseCase.postError(InternetError.Network)
            }
        }.launchIn(viewModelScope)
    }

    fun updateCountry(countryCode: String) {
        country = countryCode
        updateServices()
    }

    fun updateServices() {
        viewModelScope.launch {
            serviceUseCase.loadServices(country, cookie)
        }
    }

    private fun updateCountries() {
        viewModelScope.launch {
            serviceUseCase.loadCountries(cookie)
        }
    }

    fun updateBalance() {
        viewModelScope.launch {
            serviceUseCase.updateBalance(apiKey)
        }
    }

    fun getNumber(serviceID: String) {
        viewModelScope.launch {
            rentedNumberUseCase.getNumber(apiKey, country, serviceID)
        }
    }
}