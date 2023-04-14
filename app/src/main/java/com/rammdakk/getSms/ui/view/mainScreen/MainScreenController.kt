package com.rammdakk.getSms.ui.view.mainScreen

import android.content.Context
import android.util.Log
import android.webkit.WebView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.LogInScreen
import com.rammdakk.getSms.R
import com.rammdakk.getSms.WebViewScreen
import com.rammdakk.getSms.data.net.api.Result
import com.rammdakk.getSms.data.net.api.error.ErrorMessageConverterImpl
import com.rammdakk.getSms.data.net.api.error.InternetError
import com.rammdakk.getSms.databinding.FragmentMainScreenBinding
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.CustomWebViewClient
import com.rammdakk.getSms.ioc.WebViewLoadHandler
import com.rammdakk.getSms.ioc.mainScreen.TopUpBalanceWebViewLoadHandlerImpl
import com.rammdakk.getSms.ui.stateholders.MainScreenViewModel
import com.rammdakk.getSms.ui.view.CustomSnackbar


class MainScreenController(
    private var binding: FragmentMainScreenBinding,
    private var lifecycleOwner: LifecycleOwner,
    private var viewModel: MainScreenViewModel,
    private var navigator: AppNavigator
) {

    private var snackbar: Snackbar? = null
    fun setUpViews() {
        setUpButtons()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.balance.observe(lifecycleOwner) {
            binding.balanceSumTW.text =
                binding.root.context.resources.getString(R.string.balance, it)
        }
        viewModel.errors.observe(lifecycleOwner) {
            handleError(it)
        }
    }

    private fun handleError(error: Result.Error<String>?) {
        if (error?.type == InternetError.AccessDenied) {
            navigator.navigateTo(LogInScreen)
        }
        if (error == null) {
            snackbar?.dismiss()
            return
        }
        snackbar = CustomSnackbar(binding.root).showSnackBar(
            ErrorMessageConverterImpl(
                binding.root.context.resources
            ).getError(error.type, error.details ?: ""),
            if (error.type == InternetError.Network) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG,
            viewModel::refreshAll
        )
    }

    private fun setUpButtons() {
        binding.topUpBalanceTW.setOnClickListener {
            navigator.navigateTo(
                WebViewScreen(
                    UrlLinks.URL_BALANCE,
                    TopUpBalanceWebViewLoadHandlerImpl()
                ), true
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
                .loadUrl(UrlLinks.URL_LOGOUT).apply {
                    navigator.navigateTo(LogInScreen)
                }
        }
    }
}
