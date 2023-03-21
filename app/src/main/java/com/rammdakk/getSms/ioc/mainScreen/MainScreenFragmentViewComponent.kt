package com.rammdakk.getSms.ioc.mainScreen

import androidx.lifecycle.LifecycleOwner
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.databinding.FragmentMainScreenBinding
import com.rammdakk.getSms.ui.view.mainScreen.MainScreenController

class MainScreenFragmentViewComponent(
    fragmentComponent: MainScreenFragmentComponent,
    binding: FragmentMainScreenBinding,
    lifecycleOwner: LifecycleOwner,
    navigator: AppNavigator
) {
    val tasksViewController = MainScreenController(
        binding,
        lifecycleOwner,
        fragmentComponent.viewModel,
        navigator
    )
}