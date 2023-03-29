package com.rammdakk.getSms.ioc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rammdakk.getSms.connectvity.ConnectivityObserver
import com.rammdakk.getSms.domain.usecases.ErrorsUseCase
import com.rammdakk.getSms.domain.usecases.RentedNumberUseCase
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import com.rammdakk.getSms.ui.stateholders.MainScreenViewModel
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel
import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val serviceUseCase: ServiceUseCase,
    private val errorsUseCase: ErrorsUseCase,
    private val rentedNumberUseCase: RentedNumberUseCase,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        MainScreenViewModel::class.java -> MainScreenViewModel(
            serviceUseCase, errorsUseCase
        )
        ServiceScreenViewModel::class.java -> ServiceScreenViewModel(
            serviceUseCase, connectivityObserver
        )
        RentedNumbersViewModel::class.java -> RentedNumbersViewModel(
            rentedNumberUseCase, connectivityObserver
        )
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}