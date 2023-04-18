package com.rammdakk.getSms.ui.view.rentedNumbers

import android.os.Handler
import android.os.Looper
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

    private lateinit var timerHandler: Handler
    private var timerRunnable: Runnable? = null
    private var updateInterval = 10000L

    fun setUpViews() {
        setUpList()
        setUpSwipeToRefresh()
        setUpAutoRefresh()
    }

    private fun setUpList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = adapter
        viewModel.numbers.observe(lifecycleOwner) { numbers ->
            adapter.submitList(numbers)
            binding.swipeRefreshLayout.isRefreshing = false
            setUpAutoRefresh()
        }
        viewModel.status.observe(lifecycleOwner) {
            Log.d("Ramil", "Status update $it")
            viewModel.getActiveNumbers()
        }
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getActiveNumbers()
        }

    }

    private fun setUpAutoRefresh() {
        removeCallbacks()
        timerHandler = Handler(Looper.getMainLooper())
        timerRunnable = object : Runnable {
            override fun run() {
                removeCallbacks()
                updateInterval =
                    if (viewModel.numbers.value?.isNotEmpty() == true) 10000L else 40000L
                viewModel.getActiveNumbers()
                timerHandler.postDelayed(this, updateInterval)
            }
        }
        timerHandler.postDelayed(timerRunnable!!, updateInterval)
    }

    fun removeCallbacks() {
        timerRunnable?.let { timerHandler.removeCallbacks(it) }
    }
}