package com.rammdakk.getSms.ui.view.mainScreen

import android.content.Context
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.LogInScreen
import com.rammdakk.getSms.R
import com.rammdakk.getSms.WebViewScreen
import com.rammdakk.getSms.databinding.FragmentMainScreenBinding
import com.rammdakk.getSms.ioc.CustomWebViewClient
import com.rammdakk.getSms.ioc.WebViewLoadHandler
import com.rammdakk.getSms.ioc.mainScreen.TopUpBalanceWebViewLoadHandlerImpl
import com.rammdakk.getSms.ui.stateholders.MainScreenViewModel


class MainScreenController(
    private var binding: FragmentMainScreenBinding,
    private var lifecycleOwner: LifecycleOwner,
    private var viewModel: MainScreenViewModel,
    private var adapter: SmsInfoHolderAdapter,
    private var navigator: AppNavigator
) {
    fun setUpViews() {
        setUpButtons()
        setUpTasksList()
        setUpSwipeToRefresh()
        setUpCountrySpinner()
    }

    private fun setUpCountrySpinner() {
        val spinner = binding.countrySpinner
        spinner.adapter =
            CountrySpinnerAdapter(binding.root.context, R.layout.spinner_subitem, mutableListOf())
        viewModel.countries.observe(lifecycleOwner) { countriesList ->
            (spinner.adapter as CountrySpinnerAdapter).updateData(countriesList)
            spinner.setSelection(countriesList.indexOf(countriesList.find { it.countryCode == "ru" }))
        }
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val countryCode =
                    (spinner.adapter as CountrySpinnerAdapter).getItem(position)?.countryCode
                countryCode?.let { viewModel.updateCountry(it) }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
    }

    private fun setUpButtons() {
        binding.topUpBalanceTW.setOnClickListener {
            navigator.navigateTo(
                WebViewScreen(
                    "https://vak-sms.com/pay/",
                    TopUpBalanceWebViewLoadHandlerImpl()
                )
            )
        }
        binding.signoutIW.setOnClickListener {
            it.context.getSharedPreferences(
                "com.rammdakk.getSms", Context.MODE_PRIVATE
            ).edit().remove("accessKey").apply()
            WebView(it.context).apply {
                webViewClient = CustomWebViewClient(object : WebViewLoadHandler {
                    override fun handleLoading(webView: WebView, url: String) {
                        Log.d("URL", url)
                    }
                })
                isVisible = false
            }
                .loadUrl("https://vak-sms.com/accounts/logout/?next=/lk/")
            navigator.navigateTo(LogInScreen)
        }
    }

    private fun setUpTasksList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = adapter
        viewModel.services.observe(lifecycleOwner) { newService ->
            adapter.submitList(newService)
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.balance.observe(lifecycleOwner) {
            binding.balanceSumTW.text = "${it} р."
        }
        binding.searchViewEditText.doOnTextChanged { text, _, _, _ ->
            (binding.recyclerView.adapter as SmsInfoHolderAdapter).updateList { it ->
                it.serviceName.lowercase().startsWith(
                    (text ?: "").toString().lowercase()
                )
            }
        }
    }


    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateServices()
            viewModel.updateBalance()
        }
    }


}
