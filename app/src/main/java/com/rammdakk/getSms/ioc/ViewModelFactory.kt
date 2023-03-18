package com.rammdakk.getSms.ioc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import com.rammdakk.getSms.ui.mainScreen.MainScreenViewModel
import com.rammdakk.getSms.connectvity.ConnectivityObserver
import java.io.InputStream
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val serviceUseCase: ServiceUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        MainScreenViewModel::class.java -> MainScreenViewModel(
            serviceUseCase,
            connectivityObserver
        )
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}