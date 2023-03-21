package com.rammdakk.getSms.ioc.mainScreen

import com.rammdakk.getSms.ui.view.mainScreen.MainScreenFragment
import com.rammdakk.getSms.ui.stateholders.MainScreenViewModel
import com.rammdakk.getSms.ui.view.mainScreen.ServicesItemsDiffCalc
import com.rammdakk.getSms.ui.view.mainScreen.SmsInfoHolderAdapter

class MainScreenFragmentComponent(
    val fragment: MainScreenFragment,
    val viewModel: MainScreenViewModel
) {
    val adapter = SmsInfoHolderAdapter(viewModel, ServicesItemsDiffCalc(), fragment)
}