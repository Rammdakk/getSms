package com.rammdakk.getSms.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.getSms.connectvity.ConnectivityObserver
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val serviceUseCase: ServiceUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    val services = serviceUseCase.services
    val balance = serviceUseCase.balance
    val countries = serviceUseCase.countries

    private var apiKey = ""
    private var country = "ru"

    fun configure(key: String) {
        apiKey = key
        updateServices()
        updateBalance()
        updateCountries()
        connectivityObserver.observe().onEach {
            serviceUseCase.loadServices(apiKey)
            if (it == ConnectivityObserver.Status.Available) {
                serviceUseCase.updateBalance(apiKey)
            }
        }.launchIn(viewModelScope)
    }

    fun updateCountry(countryCode: String) {
        country = countryCode
        updateServices()
    }

    fun updateServices() {
        viewModelScope.launch {
            serviceUseCase.loadServices(apiKey, country)
        }
    }

    private fun updateCountries() {
        viewModelScope.launch {
            serviceUseCase.loadCountries()
        }
    }

    fun updateBalance() {
        viewModelScope.launch {
            serviceUseCase.updateBalance(apiKey)
        }
    }


}