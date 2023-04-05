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
import com.rammdakk.getSms.ioc.CustomWebViewClient
import com.rammdakk.getSms.ioc.activeNumbersScreen.GetActiveNumberHandlerImpl
import com.rammdakk.getSms.ioc.activeNumbersScreen.RentedNumbersFragmentComponent
import com.rammdakk.getSms.ioc.activeNumbersScreen.RentedNumbersFragmentViewComponent
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel

class RentedNumbersFragment(private val apiKey: String, private val cookie: String) : Fragment() {

    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private lateinit var binding: FragmentRentedNumbersBinding
    private lateinit var fragmentComponent: RentedNumbersFragmentComponent
    private var fragmentViewComponent: RentedNumbersFragmentViewComponent? = null

    private val viewModel: RentedNumbersViewModel by viewModels { applicationComponent.getViewModelFactory() }

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
        viewModel.configure(apiKey, cookie)
        binding = FragmentRentedNumbersBinding.inflate(layoutInflater, container, false)
        val webView = binding.ww
        webView.webViewClient =
            CustomWebViewClient(loadHandler = GetActiveNumberHandlerImpl(viewModel))
        webView.settings.javaScriptEnabled = true
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
        binding.ww.loadUrl("https://vak-sms.com/getNumber/")
        viewModel.updateBalance()
        super.onResume()
    }

    override fun onDestroyView() {
        fragmentViewComponent?.rentedNumbersScreenController?.removeCallbacks()
        super.onDestroyView()
    }

    companion object {
        fun newInstance(apiKey: String, cookie: String) = RentedNumbersFragment(apiKey, cookie)
    }
}