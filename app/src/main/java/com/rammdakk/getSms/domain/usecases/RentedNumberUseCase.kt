package com.rammdakk.getSms.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rammdakk.getSms.data.core.model.RentedNumber
import com.rammdakk.getSms.data.net.api.error.InternetError
import com.rammdakk.getSms.data.net.model.NumberResponse
import com.rammdakk.getSms.data.net.model.StatusResponse
import com.rammdakk.getSms.data.net.repository.ServiceRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class RentedNumberUseCase @Inject constructor(private val servicesRepository: ServiceRepository) {

    private val _number = MediatorLiveData<NumberResponse>()
    val number: LiveData<NumberResponse> = _number

    private val _status = MediatorLiveData<StatusResponse>()
    val status: LiveData<StatusResponse> = _status

    private val _rentedNumbers = MediatorLiveData<List<RentedNumber>>()
    val rentedNumbers: LiveData<List<RentedNumber>> = _rentedNumbers

    private val combineCoroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    init {
        _number.addSource(servicesRepository.number) {
            updateNumberResponseLiveData()
        }
        _status.addSource(servicesRepository.status) {
            updateStatusResponseLiveData()
        }
        _rentedNumbers.addSource(servicesRepository.rentedNumbers) {
            updateRentedNumbersResponseLiveData()
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

    private fun updateRentedNumbersResponseLiveData() {
        combineCoroutineScope.launch {
            _rentedNumbers.postValue(servicesRepository.rentedNumbers.value)
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

    suspend fun getNumbers(cookie: String) {
        withContext(Dispatchers.IO) {
            servicesRepository.getNumbers(cookie)
        }
    }
}