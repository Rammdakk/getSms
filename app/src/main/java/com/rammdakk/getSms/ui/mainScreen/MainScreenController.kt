package com.rammdakk.getSms.ui.mainScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.LogInScreen
import com.rammdakk.getSms.WebViewScreen
import com.rammdakk.getSms.databinding.FragmentMainScreenBinding

class MainScreenController(
    private var binding: FragmentMainScreenBinding,
    private var lifecycleOwner: LifecycleOwner,
    private var viewModel: MainScreenViewModel,
    private var adapter: SmsInfoHolderAdapter,
    private var navigator: AppNavigator
) {
    fun setUpViews() {
        setUpErrorsHandling()
        setUpButtons()
        setUpTasksList()
        setUpSwipeToRefresh()
    }

    private fun setUpErrorsHandling() {
//        TODO("Not implemented")
    }

    private fun setUpButtons() {
        binding.topUpBalanceTW.setOnClickListener {
            navigator.navigateTo(WebViewScreen("https://vak-sms.com/pay/"))
        }
        binding.signoutIW.setOnClickListener {
            it.context.getSharedPreferences(
                "com.rammdakk.getSms", Context.MODE_PRIVATE
            ).edit().remove("accessKey").apply()
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
    }


    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.d("update", "update")
            viewModel.updateServices()
        }
    }


}
