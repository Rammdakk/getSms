package com.rammdakk.getSms.ui.view.serviceScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.rammdakk.getSms.data.core.model.Service
import com.rammdakk.getSms.databinding.ServiceCellBinding
import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel

class ServiceViewHolderAdapter(
    private val viewModel: ServiceScreenViewModel,
    servicesItemsDiffCalc: ServicesItemsDiffCalc
) : ListAdapter<Service, ServiceViewHolder>(servicesItemsDiffCalc) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ServiceCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateList(filter: (Service) -> Boolean) {
        val filtered = viewModel.services.value?.filter { filter(it) }
        this.submitList(filtered)
    }
}