package com.rammdakk.getSms.ui.mainScreen

import androidx.recyclerview.widget.DiffUtil
import com.rammdakk.getSms.data.Service

class ServicesItemsDiffCalc : DiffUtil.ItemCallback<com.rammdakk.getSms.data.Service>() {
    override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem.ServiceName == newItem.ServiceName
    }

    override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean {
        return oldItem == newItem
    }

}