package com.rammdakk.getSms.ui.view.rentedNumbers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rammdakk.getSms.App
import com.rammdakk.getSms.databinding.FragmentRentedNumbersBinding
import com.rammdakk.getSms.ioc.activeNumbersScreen.RentedNumbersFragmentComponent
import com.rammdakk.getSms.ioc.activeNumbersScreen.RentedNumbersFragmentViewComponent
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel

class RentedNumbersFragment : Fragment() {

    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private lateinit var binding: FragmentRentedNumbersBinding
    private lateinit var fragmentComponent: RentedNumbersFragmentComponent
    private var fragmentViewComponent: RentedNumbersFragmentViewComponent? = null

    private val viewModel: RentedNumbersViewModel by viewModels { applicationComponent.getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate", "RentedNumbersFragment")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("onCreateView", "RentedNumbersFragment")
        binding = FragmentRentedNumbersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        val webView = WebView(binding.root.context)
//        webView.webViewClient = CustomWebViewClient(loadHandler = GetActiveNumberHandlerImpl())
        webView.isVisible = false
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://vak-sms.com/getNumber/")
        Log.d("onResume", "RentedNumbersFragment")
        super.onResume()
    }

    companion object {
        fun newInstance() = RentedNumbersFragment()
    }
}