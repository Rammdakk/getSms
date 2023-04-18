package com.rammdakk.getSms.ui.view.mainScreen

import android.content.Context
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
            ).edit().apply {
                remove("accessKey").apply()
                remove("cookies").apply()
            }
            navigator.navigateTo(LogInScreen)
        }
    }
}
