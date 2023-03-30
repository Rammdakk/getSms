package com.rammdakk.getSms.ui.view.rentedNumbers

import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rammdakk.getSms.core.model.RentedNumber
import com.rammdakk.getSms.databinding.NumberCellBinding
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel

class NumberViewHolder(
    private val numberCellBinding: NumberCellBinding,
    viewModel: RentedNumbersViewModel,
) : RecyclerView.ViewHolder(numberCellBinding.root) {

    fun bind(rentedNumber: RentedNumber) {
        val context = numberCellBinding.root.context
        val res = context.resources
        numberCellBinding.numberServiceNameTW.text = rentedNumber.serviceName
        numberCellBinding.numberTW.text = rentedNumber.number

        numberCellBinding.smsCodeSpinner.adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item, listOf("26357", "234223")
        )
        if (rentedNumber.codes.isNotEmpty()) {
            numberCellBinding.getAnotherSmsTW.isVisible = true
            numberCellBinding.getAnotherSmsTW.setOnClickListener {
                TODO("update data")
            }
        } else {
            numberCellBinding.getAnotherSmsTW.isVisible = false
        }
        numberCellBinding.cancelNumberTW.setOnClickListener {
            TODO("Cancel number")
        }
    }
}