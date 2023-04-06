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
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import com.rammdakk.getSms.ioc.ResultHandler
import kotlinx.coroutines.launch

class RentedNumbersViewModel(
    private val serviceUseCase: ServiceUseCase,
    private val rentedNumberUseCase: RentedNumberUseCase
) : ViewModel(), ResultHandler<List<RentedNumber>> {
    private var oldNumbers: List<RentedNumber> = emptyList()
    private val _numbers = MediatorLiveData<List<RentedNumber>>()
    val numbers: LiveData<List<RentedNumber>> = _numbers
    val status = rentedNumberUseCase.status

    var numbersForPush: List<RentedNumber> = emptyList()

    private var apiKey = ""
    private var cookie = ""

    fun configure(key: String, cookie: String) {
        apiKey = key
        this.cookie = cookie
    }

    override fun onSuccess(data: List<RentedNumber>) {
        Log.d("LIVE", data.toString())
        val filteredData = data.filterNotNull()
        viewModelScope.launch {
            _numbers.postValue(filteredData)
        }
        viewModelScope.launch {
            val newForPush = filteredData.filter { number ->
                !(oldNumbers.any { (it.numberId == number.numberId && it.codes == number.codes) }
                    ?: true)
            }.filter { it.codes != "Ожидает SMS" }
            numbersForPush = newForPush
            oldNumbers = filteredData
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

    fun updateBalance() {
        viewModelScope.launch {
            serviceUseCase.updateBalance(apiKey)
        }
    }
}