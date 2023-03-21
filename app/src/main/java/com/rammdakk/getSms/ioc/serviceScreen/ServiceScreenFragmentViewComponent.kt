package com.rammdakk.getSms.ioc.serviceScreen

import androidx.lifecycle.LifecycleOwner
import com.rammdakk.getSms.databinding.FragmentServicesScreenBinding
import com.rammdakk.getSms.ui.view.serviceScreen.ServiceScreenController

class ServiceScreenFragmentViewComponent(
    fragmentComponent: ServiceScreenFragmentComponent,
    binding: FragmentServicesScreenBinding,
    lifecycleOwner: LifecycleOwner,
) {
    val servicesViewController = ServiceScreenController(
        binding,
        lifecycleOwner,
        fragmentComponent.viewModel,
        fragmentComponent.adapter
    )
}