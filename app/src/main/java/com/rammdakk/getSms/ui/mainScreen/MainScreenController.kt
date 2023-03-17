package com.rammdakk.getSms.ui.mainScreen

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rammdakk.getSms.databinding.FragmentMainScreenBinding

class MainScreenController(
    private var binding: FragmentMainScreenBinding,
    private var lifecycleOwner: LifecycleOwner,
    private var viewModel: MainScreenViewModel,
    private var adapter: SmsInfoHolderAdapter
) {
    fun setUpViews() {
//        setUpErrorsHandling()
        setUpTasksList()
        setUpSwipeToRefresh()
    }

//    private fun setUpErrorsHandling() {
//        TODO("Not implemented")
//    }

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
