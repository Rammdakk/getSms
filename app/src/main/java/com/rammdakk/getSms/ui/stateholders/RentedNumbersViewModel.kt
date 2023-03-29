package com.rammdakk.getSms.ui.stateholders

import androidx.lifecycle.ViewModel
import com.rammdakk.getSms.connectvity.ConnectivityObserver
import com.rammdakk.getSms.domain.usecases.RentedNumberUseCase

class RentedNumbersViewModel(
    private val rentedNumberUseCase: RentedNumberUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

}