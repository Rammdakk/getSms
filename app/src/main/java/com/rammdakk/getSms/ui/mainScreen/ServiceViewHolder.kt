package com.rammdakk.getSms.ui.mainScreen

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.network.HttpException
import com.rammdakk.getSms.R
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.databinding.ServiceCellBinding


class ServiceViewHolder(
    private val serviceCellBinding: ServiceCellBinding,
    viewModel: MainScreenViewModel,
    private val onClickListener: ChatListClickListener
) : RecyclerView.ViewHolder(serviceCellBinding.root) {
    fun bind(service: Service) {
        Log.d("srer", service.serviceName)
        serviceCellBinding.serviceNameTW.text = service.serviceName
        serviceCellBinding.servicePriceTW.text = "${service.price}₽"
        serviceCellBinding.serviceIW.load(service.imageUrl) {
            this.listener(onError = { request, ex ->
                if ((ex as HttpException).response.code == 404) {
                    serviceCellBinding.serviceIW.load("https://vak-sms.com/static/default.png")
                }
            })
        }
    }
}