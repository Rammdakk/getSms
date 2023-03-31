package com.rammdakk.getSms.ui.view.rentedNumbers

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.rammdakk.getSms.databinding.FragmentRentedNumbersBinding
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel
import java.util.*

class RentedNumbersScreenController(
    private var binding: FragmentRentedNumbersBinding,
    private var lifecycleOwner: LifecycleOwner,
    private var viewModel: RentedNumbersViewModel,
    private var adapter: RentedNumberViewHolderAdapter
) {

    private lateinit var timerHandler: Handler
    private var timerRunnable: Runnable? = null
    private var updateInterval = 60000L

    fun setUpViews() {
        setUpList()
        setUpSwipeToRefresh()
        setUpAutoRefresh()
    }


    private fun setUpList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = adapter
        viewModel.numbers.observe(lifecycleOwner) { numbers ->
            updateInterval = if (numbers.isEmpty()) 60000L else 15000L
            adapter.submitList(numbers)
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.status.observe(lifecycleOwner) {
//            Log.d("Ramil", "Status update $it")
//            binding.ww.reload()
        }
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.ww.reload()
        }
    }

    private fun setUpAutoRefresh() {
        removeCallbacks()
        timerHandler = Handler(Looper.getMainLooper())
        timerRunnable = object : Runnable {
            override fun run() {
                Log.d("setUpAutoRefresh", "Runnable ${Date(System.currentTimeMillis())}")
                binding.ww.reload()
                timerHandler.postDelayed(this, updateInterval)
            }
        }
        timerHandler.postDelayed(timerRunnable!!, updateInterval)
    }

    fun removeCallbacks() {
        timerRunnable?.let { timerHandler.removeCallbacks(it) }
    }

}