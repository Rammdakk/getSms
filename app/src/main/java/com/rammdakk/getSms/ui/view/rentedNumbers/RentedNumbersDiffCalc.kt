package com.rammdakk.getSms.ui.view.rentedNumbers

import androidx.recyclerview.widget.DiffUtil
import com.rammdakk.getSms.data.core.model.RentedNumber

class RentedNumbersDiffCalc : DiffUtil.ItemCallback<RentedNumber>() {
    override fun areItemsTheSame(oldItem: RentedNumber, newItem: RentedNumber): Boolean {
        return oldItem.numberId == newItem.numberId
    }

    override fun areContentsTheSame(oldItem: RentedNumber, newItem: RentedNumber): Boolean {
        return oldItem == newItem
    }

}