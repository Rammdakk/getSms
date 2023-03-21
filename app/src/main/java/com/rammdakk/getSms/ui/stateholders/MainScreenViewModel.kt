package com.rammdakk.getSms.ui.stateholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val serviceUseCase: ServiceUseCase
) : ViewModel() {
    val balance = serviceUseCase.balance
    val errors = serviceUseCase.error

    private var apiKey = ""

    fun configure(key: String) {
        apiKey = key
    }


    fun updateBalance() {
        viewModelScope.launch {
            serviceUseCase.updateBalance(apiKey)
        }
    }


}