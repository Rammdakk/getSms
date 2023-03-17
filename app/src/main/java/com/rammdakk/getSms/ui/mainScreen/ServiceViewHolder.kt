package com.rammdakk.getSms.ui.mainScreen

import androidx.recyclerview.widget.RecyclerView
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.databinding.ServiceCellBinding


class ServiceViewHolder(
    private val serviceCellBinding: ServiceCellBinding,
    viewModel: MainScreenViewModel,
    private val onClickListener: ChatListClickListener
) : RecyclerView.ViewHolder(serviceCellBinding.root) {
     fun bind(service: Service) {
         val context = serviceCellBinding.root.context
         serviceCellBinding.serviceTW.text = service.serviceName
         serviceCellBinding.serviceBtn.setOnClickListener {
             
         }
     }
}