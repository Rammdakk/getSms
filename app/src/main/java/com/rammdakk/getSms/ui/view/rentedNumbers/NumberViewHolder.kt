package com.rammdakk.getSms.ui.view.rentedNumbers

import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.network.HttpException
import com.rammdakk.getSms.R
import com.rammdakk.getSms.core.model.RentedNumber
import com.rammdakk.getSms.databinding.NumberCellBinding
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel

class NumberViewHolder(
    private val numberCellBinding: NumberCellBinding,
    viewModel: RentedNumbersViewModel,
) : RecyclerView.ViewHolder(numberCellBinding.root) {

    fun bind(rentedNumber: RentedNumber) {
        val context = numberCellBinding.root.context
        if (service.quantity < THRESHOLD_VALUE) {
            numberCellBinding.root.backgroundTintList =
                context.getColorStateList(R.color.bittersweet)
        } else {
            numberCellBinding.root.backgroundTintList = context.getColorStateList(R.color.mantis)
        }
        val res = context.resources
        numberCellBinding.serviceNameTW.text = service.serviceName
        numberCellBinding.servicePriceTW.text =
            res.getString(R.string.price, service.price)
        numberCellBinding.serviceQuantityTW.text =
            res.getString(R.string.quantity, service.quantity)
        numberCellBinding.serviceIW.load(service.imageUrl) {
            this.listener(onError = { _, ex ->
                if ((ex as HttpException).response.code == 404) {
                    numberCellBinding.serviceIW.load(UrlLinks.URL_DEFAULT_IMAGE)
                }
            })
        }
        numberCellBinding.getNumberTW.setOnClickListener {
            onClickListener.onChatListItemClick(
                service
            )
        }
    }
}