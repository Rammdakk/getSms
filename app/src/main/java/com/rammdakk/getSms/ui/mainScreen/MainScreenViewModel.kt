package com.rammdakk.getSms.ui.mainScreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.getSms.connectvity.ConnectivityObserver
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.InputStream

class MainScreenViewModel(
    private val serviceUseCase: ServiceUseCase,
    connectivityObserver: ConnectivityObserver,
) : ViewModel() {
    val services = serviceUseCase.services

    val offlineMessage: MutableLiveData<String> = MutableLiveData("")

    init {
        offlineMessage.postValue("Ошибка: Нет доступа к интернету")
        connectivityObserver.observe().onEach {
            if (it == ConnectivityObserver.Status.Available) {
                serviceUseCase.loadServices()
                offlineMessage.postValue("")
            } else {
                offlineMessage.postValue("Ошибка: Нет доступа к интернету")
            }
        }.launchIn(viewModelScope)
    }

    fun updateServices() {
        viewModelScope.launch {
            Log.d("update", "VM updateServices")
            serviceUseCase.loadServices()
        }
    }

    // TODO: Implement the ViewModel
}