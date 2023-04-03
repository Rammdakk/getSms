package com.rammdakk.getSms.ui.view.serviceScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rammdakk.getSms.App
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.databinding.FragmentServicesScreenBinding
import com.rammdakk.getSms.ioc.serviceScreen.ServiceScreenFragmentComponent
import com.rammdakk.getSms.ioc.serviceScreen.ServiceScreenFragmentViewComponent
import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel

class ServiceScreenFragment(private val apiKey: String) : Fragment() {


    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private lateinit var navigator: AppNavigator
    private lateinit var binding: FragmentServicesScreenBinding
    private lateinit var fragmentComponent: ServiceScreenFragmentComponent
    private var fragmentViewComponent: ServiceScreenFragmentViewComponent? = null

    private val viewModel: ServiceScreenViewModel by viewModels { applicationComponent.getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent = ServiceScreenFragmentComponent(
            viewModel = viewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicesScreenBinding.inflate(layoutInflater)
        binding.recyclerView.isVisible = false
        viewModel.configure(apiKey)
        fragmentViewComponent =
            ServiceScreenFragmentViewComponent(
                fragmentComponent = fragmentComponent,
                binding = binding,
                lifecycleOwner = viewLifecycleOwner
            ).apply {
                servicesViewController.setUpViews()
            }
        return binding.root
    }

    override fun onResume() {
        viewModel.updateServices()
        viewModel.updateBalance()
        super.onResume()
    }


    companion object {
        fun newInstance(apiKey: String): ServiceScreenFragment {
            return ServiceScreenFragment(apiKey)
        }
    }

}