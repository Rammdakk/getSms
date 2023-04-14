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
    private var cookie = ""

    fun configure(key: String, cookie: String) {
        apiKey = key
        this.cookie = cookie
    }


    fun refreshAll() {
        viewModelScope.launch {
            serviceUseCase.updateBalance(apiKey)
            serviceUseCase.loadCountries(cookie)
            serviceUseCase.loadServices("ru", cookie)
        }
    }


}