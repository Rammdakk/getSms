package com.rammdakk.getSms.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rammdakk.getSms.data.net.api.Result
import com.rammdakk.getSms.data.net.repository.ServiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ErrorsUseCase @Inject constructor(private val servicesRepository: ServiceRepository) {


    private val _error = MediatorLiveData<Result.Error<String>?>()
    val error: LiveData<Result.Error<String>?> = _error

    private val combineCoroutineScope = CoroutineScope(Job() + Dispatchers.Default)


    init {
        _error.addSource(servicesRepository.error) {
            updateErrorLiveData()
        }
    }

    private fun updateErrorLiveData() {
        combineCoroutineScope.launch {
            _error.postValue(servicesRepository.error.value)
        }
    }
}