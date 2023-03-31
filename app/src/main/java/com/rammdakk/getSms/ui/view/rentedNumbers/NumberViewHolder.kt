package com.rammdakk.getSms.ui.view.rentedNumbers

import androidx.recyclerview.widget.RecyclerView
import com.rammdakk.getSms.R
import com.rammdakk.getSms.core.model.NumberStatus
import com.rammdakk.getSms.core.model.RentedNumber
import com.rammdakk.getSms.databinding.NumberCellBinding
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel

class NumberViewHolder(
    private val numberCellBinding: NumberCellBinding,
    private val viewModel: RentedNumbersViewModel,
) : RecyclerView.ViewHolder(numberCellBinding.root) {

    fun bind(rentedNumber: RentedNumber) {
        val context = numberCellBinding.root.context
        val res = context.resources
        numberCellBinding.numberServiceNameTW.text = rentedNumber.serviceName
        numberCellBinding.numberTW.text = rentedNumber.number

        numberCellBinding.smsCodeSpinner.text = rentedNumber.codes
        if (rentedNumber.codes == "Ожидает SMS") {
            numberCellBinding.cancelNumberTW.text = res.getString(R.string.cancel_number)
//            numberCellBinding.getAnotherSmsTW.isVisible = true
//            numberCellBinding.cancelNumberTW.isVisible = false
            numberCellBinding.cancelNumberTW.setOnClickListener {
                viewModel.setStatus(NumberStatus.CANCEL, rentedNumber.numberId)
            }
        } else {
//            numberCellBinding.getAnotherSmsTW.isVisible = false
//            numberCellBinding.cancelNumberTW.isVisible = true
            numberCellBinding.cancelNumberTW.text = res.getString(R.string.get_another_sms)
            numberCellBinding.cancelNumberTW.setOnClickListener {
                viewModel.setStatus(NumberStatus.ONE_MORE, rentedNumber.numberId)
            }
        }
    }
}