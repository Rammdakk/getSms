package com.rammdakk.getSms.ui.stateholders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammdakk.getSms.data.core.model.NumberStatus
import com.rammdakk.getSms.data.core.model.RentedNumber
import com.rammdakk.getSms.domain.usecases.RentedNumberUseCase
import com.rammdakk.getSms.domain.usecases.ServiceUseCase
import kotlinx.coroutines.launch

class RentedNumbersViewModel(
    private val serviceUseCase: ServiceUseCase,
    private val rentedNumberUseCase: RentedNumberUseCase
) : ViewModel() {
    val numbers: LiveData<List<RentedNumber>> = rentedNumberUseCase.rentedNumbers
    val status = rentedNumberUseCase.status

    private var oldNumbers: List<RentedNumber> = emptyList()
    private val _numbersForPush = MediatorLiveData<List<RentedNumber>>()
    var numbersForPush: LiveData<List<RentedNumber>> = _numbersForPush

    private var apiKey = ""
    private var cookie = ""

    fun configure(key: String, cookie: String) {
        apiKey = key
        this.cookie = cookie

        _numbersForPush.addSource(numbers) {
            viewModelScope.launch {
                val newForPush = it.filter { number ->
                    !(oldNumbers.any { (it.numberId == number.numberId && it.codes == number.codes) }
                        ?: true)
                }
                _numbersForPush.postValue(newForPush)
                oldNumbers = it
            }
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

    fun getActiveNumbers() {
        viewModelScope.launch {
            rentedNumberUseCase.getNumbers(cookie)
        }
    }
}