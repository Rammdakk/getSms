package com.rammdakk.getSms.ui.view.mainScreen

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.network.HttpException
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.databinding.ServiceCellBinding
import com.rammdakk.getSms.ui.stateholders.MainScreenViewModel


class ServiceViewHolder(
    private val serviceCellBinding: ServiceCellBinding,
    viewModel: MainScreenViewModel,
    private val onClickListener: ChatListClickListener
) : RecyclerView.ViewHolder(serviceCellBinding.root) {
    fun bind(service: Service) {
        serviceCellBinding.serviceNameTW.text = service.serviceName
        serviceCellBinding.servicePriceTW.text = "${service.price}₽"
        serviceCellBinding.serviceIW.load(service.imageUrl) {
            this.listener(onError = { _, ex ->
                if ((ex as HttpException).response.code == 404) {
                    serviceCellBinding.serviceIW.load("https://vak-sms.com/static/default.png")
                }
            })
        }
    }
}