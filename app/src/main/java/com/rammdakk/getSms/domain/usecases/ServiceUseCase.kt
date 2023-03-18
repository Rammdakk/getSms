package com.rammdakk.getSms.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.data.repository.ServiceRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class ServiceUseCase @Inject constructor(private val servicesRepository: ServiceRepository) {
    private val _services = MediatorLiveData<List<Service>>()

    val services: LiveData<List<Service>> = _services

    private val combineCoroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    init {
        _services.addSource(servicesRepository.services) {
            updateServicesLiveData()
        }
    }

    private fun updateServicesLiveData(){
        combineCoroutineScope.launch {
            _services.postValue(servicesRepository.services.value.orEmpty())
        }
    }


    suspend fun loadServices() {
        withContext(Dispatchers.Main) {
            servicesRepository.loadServices()
        }
    }
}