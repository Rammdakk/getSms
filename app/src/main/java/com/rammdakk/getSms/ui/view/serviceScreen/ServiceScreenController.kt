package com.rammdakk.getSms.ui.view.serviceScreen

import android.view.View
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rammdakk.getSms.R
import com.rammdakk.getSms.databinding.FragmentServicesScreenBinding
import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel


class ServiceScreenController(
    private var binding: FragmentServicesScreenBinding,
    private var lifecycleOwner: LifecycleOwner,
    private var viewModel: ServiceScreenViewModel,
    private var adapter: ServiceViewHolderAdapter
) {

    fun setUpViews() {
        setUpList()
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
            binding.recyclerView.isVisible = true
        }
        viewModel.numberReference.observe(lifecycleOwner) {
            binding.root.rootView.findViewById<TabLayout>(R.id.tab_layout)
                .setScrollPosition(1, 1f, true)
            binding.root.rootView.findViewById<ViewPager>(R.id.pager).currentItem = 1
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

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
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
            val searchText = binding.searchViewEditText.text.toString().lowercase()
            adapter.submitList(newService.filter {
                it.serviceName.lowercase().startsWith(searchText)
            })
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.searchViewEditText.doOnTextChanged { text, _, _, _ ->
            (binding.recyclerView.adapter as ServiceViewHolderAdapter).updateList {
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