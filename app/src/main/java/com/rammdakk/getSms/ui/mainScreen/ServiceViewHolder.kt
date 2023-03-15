package com.rammdakk.getSms.ui.mainScreen

import androidx.recyclerview.widget.RecyclerView
import com.rammdakk.getSms.data.Service
import com.rammdakk.getSms.databinding.ServiceCellBinding


class ServiceViewHolder(
    private val serviceCellBinding: ServiceCellBinding
) : RecyclerView.ViewHolder(serviceCellBinding.root) {
     fun bind(service: Service) {
         val context = serviceCellBinding.root.context
         serviceCellBinding.serviceIW.setImageDrawable(service.image)
         serviceCellBinding.serviceTW.text = service.ServiceName
         serviceCellBinding.serviceBtn.setOnClickListener {
             
         }
     }
}