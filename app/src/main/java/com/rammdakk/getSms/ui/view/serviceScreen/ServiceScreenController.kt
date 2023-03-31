package com.rammdakk.getSms.ui.view.serviceScreen

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewpager.widget.ViewPager
import com.google.android.material.internal.ContextUtils.getActivity
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
        setUpSearch()
    }

    private fun setUpSearch() {
        binding.searchViewEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                Log.d("has", " focus")
            } else {
                val imm: InputMethodManager =
                    getActivity(binding.root.context)?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                Log.d("lost", " focus")
            }
        }
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
            adapter.submitList(newService)
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