package com.rammdakk.getSms.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rammdakk.getSms.data.api.error.InternetError
import com.rammdakk.getSms.data.model.NumberResponse
import com.rammdakk.getSms.data.model.StatusResponse
import com.rammdakk.getSms.data.repository.ServiceRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class RentedNumberUseCase @Inject constructor(private val servicesRepository: ServiceRepository) {

    private val _number = MediatorLiveData<NumberResponse>()
    val number: LiveData<NumberResponse> = _number

    private val _status = MediatorLiveData<StatusResponse>()
    val status: LiveData<StatusResponse> = _status

    private val combineCoroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    init {
        _number.addSource(servicesRepository.number) {
            updateNumberResponseLiveData()
        }
        _status.addSource(servicesRepository.status) {
            updateStatusResponseLiveData()
        }
    }

    private fun updateNumberResponseLiveData() {
        combineCoroutineScope.launch {
            _number.postValue(servicesRepository.number.value)
        }
    }

    private fun updateStatusResponseLiveData() {
        combineCoroutineScope.launch {
            _status.postValue(servicesRepository.status.value)
        }
    }

    suspend fun getNumber(apiKey: String, country: String, serviceID: String) {
        withContext(Dispatchers.IO) {
            servicesRepository.getNumber(apiKey, country, serviceID)
        }
    }

    suspend fun postError(error: InternetError, string: String = "") {
        withContext(Dispatchers.Default) {
            servicesRepository.postError(error, string)
        }
    }

    suspend fun updateStatus(status: String, numberID: String, apiKey: String) {
        withContext(Dispatchers.IO) {
            servicesRepository.setStatus(status, numberID, apiKey)
        }
    }
}