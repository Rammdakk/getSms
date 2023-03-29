package com.rammdakk.getSms.ioc.activeNumbersScreen

import androidx.lifecycle.LifecycleOwner
import com.rammdakk.getSms.databinding.FragmentServicesScreenBinding
import com.rammdakk.getSms.ui.view.rentedNumbers.RentedNumbersScreenController

class RentedNumbersFragmentViewComponent(
    fragmentComponent: RentedNumbersFragmentComponent,
    binding: FragmentServicesScreenBinding,
    lifecycleOwner: LifecycleOwner,
) {
    val rentedNumbersScreenController = RentedNumbersScreenController(
        binding,
        lifecycleOwner,
        fragmentComponent.viewModel,
        fragmentComponent.adapter
    )
}