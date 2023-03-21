package com.rammdakk.getSms.ui.view.serviceScreen

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.network.HttpException
import com.rammdakk.getSms.R
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.databinding.ServiceCellBinding
import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel


class ServiceViewHolder(
    private val serviceCellBinding: ServiceCellBinding,
    viewModel: ServiceScreenViewModel,
    private val onClickListener: ChatListClickListener
) : RecyclerView.ViewHolder(serviceCellBinding.root) {

    private val THRESHOLD_VALUE = 10

    fun bind(service: Service) {
        if (service.quantity < THRESHOLD_VALUE) {
            serviceCellBinding.root.background =
                serviceCellBinding.root.context.getDrawable(R.drawable.gradient_red)
        }
        val res = serviceCellBinding.root.context.resources
        serviceCellBinding.serviceNameTW.text = service.serviceName
        serviceCellBinding.servicePriceTW.text =
            res.getString(R.string.price, service.price)
        serviceCellBinding.serviceQuantityTW.text =
            res.getString(R.string.quantity, service.quantity)
        serviceCellBinding.serviceIW.load(service.imageUrl) {
            this.listener(onError = { _, ex ->
                if ((ex as HttpException).response.code == 404) {
                    serviceCellBinding.serviceIW.load("https://vak-sms.com/static/default.png")
                }
            })
        }
    }
}