package com.rammdakk.getSms.ui.view.rentedNumbers

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.rammdakk.getSms.databinding.FragmentRentedNumbersBinding
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel

class RentedNumbersScreenController(
    private var binding: FragmentRentedNumbersBinding,
    private var lifecycleOwner: LifecycleOwner,
    private var viewModel: RentedNumbersViewModel,
    private var adapter: RentedNumberViewHolderAdapter
) {

    fun setUpViews() {
        setUpList()
        setUpSwipeToRefresh()
    }


    private fun setUpList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = adapter
        viewModel.numbers.observe(lifecycleOwner) { numbers ->
            Log.d("setUpView", numbers.toString())
            adapter.submitList(numbers)
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.status.observe(lifecycleOwner) {
            binding.ww.reload()
        }
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.ww.reload()
        }
    }

}