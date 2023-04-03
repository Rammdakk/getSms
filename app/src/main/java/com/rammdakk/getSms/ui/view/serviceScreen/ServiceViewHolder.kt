package com.rammdakk.getSms.ui.view.serviceScreen

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.network.HttpException
import com.rammdakk.getSms.R
import com.rammdakk.getSms.core.model.Service
import com.rammdakk.getSms.databinding.ServiceCellBinding
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ui.stateholders.ServiceScreenViewModel


class ServiceViewHolder(
    private val serviceCellBinding: ServiceCellBinding,
    private val viewModel: ServiceScreenViewModel
) : RecyclerView.ViewHolder(serviceCellBinding.root) {

    private val THRESHOLD_VALUE = 10

    fun bind(service: Service) {
        val context = serviceCellBinding.root.context
        if (service.quantity < THRESHOLD_VALUE) {
            serviceCellBinding.root.backgroundTintList =
                context.getColorStateList(R.color.bittersweet)
        } else {
            serviceCellBinding.root.backgroundTintList = context.getColorStateList(R.color.mantis)
        }
        val res = context.resources
        serviceCellBinding.serviceNameTW.text = service.serviceName
        serviceCellBinding.servicePriceTW.text =
            res.getString(R.string.price, service.price)
        serviceCellBinding.serviceQuantityTW.text =
            res.getString(R.string.quantity, service.quantity)
        serviceCellBinding.serviceIW.load(service.imageUrl) {
            this.listener(onError = { _, ex ->
                if ((ex as HttpException).response.code == 404) {
                    serviceCellBinding.serviceIW.load(UrlLinks.URL_DEFAULT_IMAGE)
                }
            })
        }
        serviceCellBinding.getNumberTW.setOnClickListener {
            viewModel.getNumber(service.serviceID)
        }
    }
}