package com.rammdakk.getSms.ui.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rammdakk.getSms.App
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.R
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.databinding.FragmentMainScreenBinding
import com.rammdakk.getSms.ioc.mainScreen.MainScreenFragmentComponent
import com.rammdakk.getSms.ioc.mainScreen.MainScreenFragmentViewComponent

class MainScreenFragment : Fragment(), ChatListClickListener {

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
        binding = FragmentMainScreenBinding.inflate(layoutInflater)
        fragmentViewComponent =
            MainScreenFragmentViewComponent(
                fragmentComponent = fragmentComponent,
                binding = binding,
                lifecycleOwner = viewLifecycleOwner,
                navigator = navigator
            ).apply {
                tasksViewController.setUpViews()
            }
        return binding.root
    }

    override fun onChatListItemClick(task: Service?) {
        TODO("Not yet implemented")
    }
}