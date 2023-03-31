package com.rammdakk.getSms.ui.stateholders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.getSms.core.model.NumberStatus
import com.rammdakk.getSms.core.model.RentedNumber
import com.rammdakk.getSms.data.api.error.InternetError
import com.rammdakk.getSms.domain.usecases.RentedNumberUseCase
import com.rammdakk.getSms.ioc.ResultHandler
import kotlinx.coroutines.launch

class RentedNumbersViewModel(
    private val rentedNumberUseCase: RentedNumberUseCase
) : ViewModel(), ResultHandler<List<RentedNumber>> {
    private val _numbers = MediatorLiveData<List<RentedNumber>>()
    val numbers: LiveData<List<RentedNumber>> = _numbers
    val status = rentedNumberUseCase.status

    private var apiKey = ""

    fun configure(key: String) {
        apiKey = key
    }

    override fun onSuccess(data: List<RentedNumber>) {
        Log.d("LIVE", data.toString())
        viewModelScope.launch {
            _numbers.postValue(data.filterNotNull())
        }
    }

    override fun onError(message: String) {
        viewModelScope.launch {
            rentedNumberUseCase.postError(InternetError.Unknown, message)
        }
    }

    fun setStatus(status: NumberStatus, numberID: String) {
        viewModelScope.launch {
            rentedNumberUseCase.updateStatus(status.value, numberID, apiKey)
        }
    }

}