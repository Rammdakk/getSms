package com.rammdakk.getSms.ioc.mainScreen

import androidx.fragment.app.Fragment
import com.rammdakk.getSms.ui.mainScreen.MainScreenFragment
import com.rammdakk.getSms.ui.mainScreen.MainScreenViewModel
import com.rammdakk.getSms.ui.mainScreen.ServicesItemsDiffCalc
import com.rammdakk.getSms.ui.mainScreen.SmsInfoHolderAdapter

class MainScreenFragmentComponent(
    val fragment: MainScreenFragment,
    val viewModel: MainScreenViewModel
) {
    val adapter = SmsInfoHolderAdapter(viewModel, ServicesItemsDiffCalc(), fragment)
}