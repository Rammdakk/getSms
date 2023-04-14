package com.rammdakk.getSms.ioc.serviceScreen

import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel
import com.rammdakk.getSms.ui.view.serviceScreen.ServiceViewHolderAdapter
import com.rammdakk.getSms.ui.view.serviceScreen.ServicesItemsDiffCalc

class ServiceScreenFragmentComponent(
    val viewModel: ServiceScreenViewModel
) {
    val adapter = ServiceViewHolderAdapter(viewModel, ServicesItemsDiffCalc())
}