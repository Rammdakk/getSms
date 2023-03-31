package com.rammdakk.getSms.ioc.activeNumbersScreen

import androidx.lifecycle.LifecycleOwner
import com.rammdakk.getSms.databinding.FragmentRentedNumbersBinding
import com.rammdakk.getSms.ui.view.rentedNumbers.RentedNumbersScreenController

class RentedNumbersFragmentViewComponent(
    fragmentComponent: RentedNumbersFragmentComponent,
    binding: FragmentRentedNumbersBinding,
    lifecycleOwner: LifecycleOwner,
) {
    val rentedNumbersScreenController = RentedNumbersScreenController(
        binding,
        lifecycleOwner,
        fragmentComponent.viewModel,
        fragmentComponent.adapter
    )
}