package com.rammdakk.getSms.ioc.mainScreen

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.databinding.FragmentMainScreenBinding
import com.rammdakk.getSms.ui.mainScreen.MainScreenController
import com.rammdakk.getSms.ui.mainScreen.MainScreenFragment

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
        fragmentComponent.adapter,
        navigator
    )
}