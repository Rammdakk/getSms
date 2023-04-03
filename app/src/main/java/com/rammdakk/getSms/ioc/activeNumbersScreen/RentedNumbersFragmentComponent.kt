package com.rammdakk.getSms.ioc.activeNumbersScreen

import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel
import com.rammdakk.getSms.ui.view.rentedNumbers.RentedNumberViewHolderAdapter
import com.rammdakk.getSms.ui.view.rentedNumbers.RentedNumbersDiffCalc
import com.rammdakk.getSms.ui.view.rentedNumbers.RentedNumbersFragment

class RentedNumbersFragmentComponent(
    val fragment: RentedNumbersFragment,
    val viewModel: RentedNumbersViewModel
) {
    val adapter = RentedNumberViewHolderAdapter(viewModel, RentedNumbersDiffCalc())
}