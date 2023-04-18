package com.rammdakk.getSms.ui.view.mainScreen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rammdakk.getSms.App
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.LogInScreen
import com.rammdakk.getSms.R
import com.rammdakk.getSms.databinding.FragmentMainScreenBinding
import com.rammdakk.getSms.ioc.mainScreen.MainScreenFragmentComponent
import com.rammdakk.getSms.ioc.mainScreen.MainScreenFragmentViewComponent
import com.rammdakk.getSms.ui.stateholders.MainScreenViewModel
import com.rammdakk.getSms.ui.view.rentedNumbers.RentedNumbersFragment
import com.rammdakk.getSms.ui.view.serviceScreen.ServiceScreenFragment

class MainScreenFragment : Fragment() {

    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private lateinit var navigator: AppNavigator
    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var fragmentComponent: MainScreenFragmentComponent
    private var fragmentViewComponent: MainScreenFragmentViewComponent? = null

    companion object {
        const val TAG = "MainScreenFragment"
        fun newInstance() = MainScreenFragment()
    }

    private val viewModel: MainScreenViewModel by viewModels { applicationComponent.getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate", "MainScreenFragment")
        navigator = AppNavigator(parentFragmentManager, R.id.content_container)
        fragmentComponent = MainScreenFragmentComponent(
            fragment = this,
            viewModel = viewModel
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("onCreateView", "MainScreenFragment")
        val apiKey = context?.getSharedPreferences(
            "com.rammdakk.getSms", Context.MODE_PRIVATE
        )?.getString("accessKey", "")
        val cookie = context?.getSharedPreferences(
            "com.rammdakk.getSms", Context.MODE_PRIVATE
        )?.getString("cookies", "")
        if (apiKey == null || cookie == null) {
            navigator.navigateTo(LogInScreen)
        }
        viewModel.configure(apiKey!!, cookie!!)
        binding = FragmentMainScreenBinding.inflate(layoutInflater)
        fragmentViewComponent =
            MainScreenFragmentViewComponent(
                fragmentComponent = fragmentComponent,
                binding = binding,
                lifecycleOwner = viewLifecycleOwner,
                navigator = navigator
            ).apply {
                mainScreenController.setUpViews()
            }

        val adapter = AdapterTabPager(childFragmentManager)
        adapter.addFragment(
            listOf(
                ServiceScreenFragment.newInstance(apiKey, cookie),
                RentedNumbersFragment.newInstance(apiKey, cookie)
            ), listOf("Сервисы", "Активации")
        )
        binding.pager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.pager)
        binding.pager.currentItem = 0
        return binding.root
    }
}