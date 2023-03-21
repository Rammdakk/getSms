package com.rammdakk.getSms.ioc.serviceScreen

import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel
import com.rammdakk.getSms.ui.view.serviceScreen.ServiceScreenFragment
import com.rammdakk.getSms.ui.view.serviceScreen.ServicesItemsDiffCalc
import com.rammdakk.getSms.ui.view.serviceScreen.SmsInfoHolderAdapter

class ServiceScreenFragmentComponent(
    val fragment: ServiceScreenFragment,
    val viewModel: ServiceScreenViewModel
) {
    val adapter = SmsInfoHolderAdapter(viewModel, ServicesItemsDiffCalc(), fragment)
}