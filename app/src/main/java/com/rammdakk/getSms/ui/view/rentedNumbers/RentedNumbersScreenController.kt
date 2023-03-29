package com.rammdakk.getSms.ui.view.rentedNumbers

import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.rammdakk.getSms.databinding.FragmentServicesScreenBinding
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel

class RentedNumbersScreenController(
    private var binding: FragmentServicesScreenBinding,
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
        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.recyclerView.scrollToPosition(0)
            }
        })
        binding.recyclerView.adapter = adapter
        viewModel.services.observe(lifecycleOwner) { newService ->
            adapter.submitList(newService)
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.searchViewEditText.doOnTextChanged { text, _, _, _ ->
            (binding.recyclerView.adapter as RentedNumberViewHolderAdapter).updateList {
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