package com.rammdakk.getSms.ui.view.serviceScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rammdakk.getSms.App
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.databinding.FragmentServicesScreenBinding
import com.rammdakk.getSms.ioc.serviceScreen.ServiceScreenFragmentComponent
import com.rammdakk.getSms.ioc.serviceScreen.ServiceScreenFragmentViewComponent
import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ServiceScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ServiceScreenFragment(private val apiKey: String) : Fragment(),
    ChatListClickListener {


    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private lateinit var navigator: AppNavigator
    private lateinit var binding: FragmentServicesScreenBinding
    private lateinit var fragmentComponent: ServiceScreenFragmentComponent
    private var fragmentViewComponent: ServiceScreenFragmentViewComponent? = null

    private val viewModel: ServiceScreenViewModel by viewModels { applicationComponent.getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.configure(apiKey)
        fragmentComponent = ServiceScreenFragmentComponent(
            fragment = this,
            viewModel = viewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesScreenBinding.inflate(layoutInflater)
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

    companion object {
        @JvmStatic
        fun newInstance(apiKey: String) = ServiceScreenFragment(apiKey)
    }

    override fun onChatListItemClick(task: Service?) {
//        TODO("Not yet implemented")
    }
}