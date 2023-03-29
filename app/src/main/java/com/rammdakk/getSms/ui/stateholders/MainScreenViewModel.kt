package com.rammdakk.getSms.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.getSms.domain.usecases.ErrorsUseCase
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val serviceUseCase: ServiceUseCase,
    errorsUseCase: ErrorsUseCase
) : ViewModel() {
    val balance = serviceUseCase.balance
    val errors = errorsUseCase.error

    private var apiKey = ""

    fun configure(key: String) {
        apiKey = key
    }


    fun refreshAll() {
        viewModelScope.launch {
            serviceUseCase.updateBalance(apiKey)
            serviceUseCase.loadCountries()
            serviceUseCase.loadServices("ru")
        }
    }


}