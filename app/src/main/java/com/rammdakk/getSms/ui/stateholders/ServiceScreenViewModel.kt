package com.rammdakk.getSms.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.getSms.connectvity.ConnectivityObserver
import com.rammdakk.getSms.data.api.error.InternetError
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ServiceScreenViewModel(
    private val serviceUseCase: ServiceUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    val services = serviceUseCase.services
    val countries = serviceUseCase.countries

    private var apiKey = ""
    private var country = "ru"

    fun configure(key: String) {
        apiKey = key
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
            serviceUseCase.loadServices(country)
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