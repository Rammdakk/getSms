package com.rammdakk.getSms.ui.view.mainScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.databinding.ServiceCellBinding
import com.rammdakk.getSms.ui.stateholders.MainScreenViewModel

class SmsInfoHolderAdapter(
    private val viewModel: MainScreenViewModel,
    servicesItemsDiffCalc: ServicesItemsDiffCalc,
    private val chatListClickListener: ChatListClickListener
) : ListAdapter<Service, ServiceViewHolder>(servicesItemsDiffCalc) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ServiceCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding, viewModel, chatListClickListener)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateList(func: (Service) -> Boolean) {
        val filtered = viewModel.services.value?.filter { func(it) }
        this.submitList(filtered)
    }
}