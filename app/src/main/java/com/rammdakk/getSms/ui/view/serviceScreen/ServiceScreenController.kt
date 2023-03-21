package com.rammdakk.getSms.ui.view.serviceScreen

import android.view.View
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.rammdakk.getSms.R
import com.rammdakk.getSms.databinding.FragmentServicesScreenBinding
import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel

class ServiceScreenController(
    private var binding: FragmentServicesScreenBinding,
    private var lifecycleOwner: LifecycleOwner,
    private var viewModel: ServiceScreenViewModel,
    private var adapter: SmsInfoHolderAdapter
) {

    fun setUpViews() {
        setUpTasksList()
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

    private fun setUpTasksList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = adapter
        viewModel.services.observe(lifecycleOwner) { newService ->
            adapter.submitList(newService)
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.searchViewEditText.doOnTextChanged { text, _, _, _ ->
            (binding.recyclerView.adapter as SmsInfoHolderAdapter).updateList { it ->
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