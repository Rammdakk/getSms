package com.rammdakk.getSms.ui.stateholders

import androidx.lifecycle.ViewModel
import com.rammdakk.getSms.connectvity.ConnectivityObserver
import com.rammdakk.getSms.domain.usecases.ServiceUseCase

class ActivityViewModel(
    serviceUseCase: ServiceUseCase,
) : ViewModel() {
    val errors = serviceUseCase.error
}