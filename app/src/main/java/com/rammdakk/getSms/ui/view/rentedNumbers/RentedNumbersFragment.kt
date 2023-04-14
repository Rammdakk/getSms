package com.rammdakk.getSms.ui.view.rentedNumbers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rammdakk.getSms.App
import com.rammdakk.getSms.databinding.FragmentRentedNumbersBinding
import com.rammdakk.getSms.ioc.activeNumbersScreen.RentedNumbersFragmentComponent
import com.rammdakk.getSms.ioc.activeNumbersScreen.RentedNumbersFragmentViewComponent
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel

class RentedNumbersFragment() : Fragment() {

    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private lateinit var binding: FragmentRentedNumbersBinding
    private lateinit var fragmentComponent: RentedNumbersFragmentComponent
    private var fragmentViewComponent: RentedNumbersFragmentViewComponent? = null

    private val viewModel: RentedNumbersViewModel by viewModels { applicationComponent.getViewModelFactory() }
    private lateinit var apiKey: String
    private lateinit var cookie: String

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate", "RentedNumbersFragment")
        super.onCreate(savedInstanceState)
        fragmentComponent = RentedNumbersFragmentComponent(
            viewModel = viewModel, fragment = this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        apiKey = requireArguments().getString("apiKey")!!
        cookie = requireArguments().getString("cookie")!!
        viewModel.configure(apiKey, cookie)
        viewModel.getActiveNumbers()
        binding = FragmentRentedNumbersBinding.inflate(layoutInflater, container, false)
        fragmentViewComponent =
            RentedNumbersFragmentViewComponent(
                fragmentComponent = fragmentComponent,
                binding = binding,
                lifecycleOwner = viewLifecycleOwner
            ).apply {
                rentedNumbersScreenController.setUpViews()
            }
        return binding.root
    }

    override fun onResume() {
        viewModel.getActiveNumbers()
        viewModel.updateBalance()
        super.onResume()
    }


    override fun onDestroy() {
        fragmentViewComponent?.rentedNumbersScreenController?.removeObserver()
        fragmentViewComponent?.rentedNumbersScreenController?.removeCallbacks()
        super.onDestroy()
    }

    companion object {
        fun newInstance(apiKey: String, cookie: String): RentedNumbersFragment {
            val bundle = Bundle()
            bundle.putString("apiKey", apiKey)
            bundle.putString("cookie", cookie)
            return RentedNumbersFragment().apply {
                this.arguments = bundle
            }
        }
    }
}