package com.rammdakk.getSms.ui.view.serviceScreen

import androidx.recyclerview.widget.DiffUtil
import com.rammdakk.getSms.data.model.Service

class ServicesItemsDiffCalc : DiffUtil.ItemCallback<Service>() {
    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem.serviceName == newItem.serviceName
    }

    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem == newItem
    }

}