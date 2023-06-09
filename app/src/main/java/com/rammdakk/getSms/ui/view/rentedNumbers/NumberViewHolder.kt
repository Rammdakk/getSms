package com.rammdakk.getSms.ui.view.rentedNumbers

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rammdakk.getSms.R
import com.rammdakk.getSms.data.core.model.NumberStatus
import com.rammdakk.getSms.data.core.model.RentedNumber
import com.rammdakk.getSms.databinding.NumberCellBinding
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel


class NumberViewHolder(
    private val numberCellBinding: NumberCellBinding,
    private val viewModel: RentedNumbersViewModel,
) : RecyclerView.ViewHolder(numberCellBinding.root) {

    fun bind(rentedNumber: RentedNumber) {
        val context = numberCellBinding.root.context
        val res = context.resources
        numberCellBinding.timerTW.setTimer(rentedNumber.timeLeft - 3, ::hideItem)
        numberCellBinding.smsCodeSpinner.text = rentedNumber.codes
        numberCellBinding.smsCodeSpinner.setOnClickListener {
            val clipboard: ClipboardManager? =
                context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            clipboard?.text = numberCellBinding.smsCodeSpinner.text
        }
        numberCellBinding.numberServiceNameTW.text = rentedNumber.serviceName
        numberCellBinding.numberTW.text = rentedNumber.number
        numberCellBinding.numberTW.setOnClickListener {
            val clipboard: ClipboardManager? =
                context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            clipboard?.text = numberCellBinding.numberTW.text
        }
        if (rentedNumber.codes == "Ожидает SMS") {
            numberCellBinding.buttonNumberTW.text = res.getString(R.string.cancel_number)
            numberCellBinding.buttonNumberTW.backgroundTintList =
                context.getColorStateList(R.color.bittersweet)
            numberCellBinding.buttonNumberTW.setTextColor(context.getColor(R.color.bittersweet))
            numberCellBinding.buttonNumberTW.setOnClickListener {
                viewModel.setStatus(NumberStatus.CANCEL, rentedNumber.numberId)
            }
        } else {
            numberCellBinding.buttonNumberTW.text = res.getString(R.string.get_another_sms)
            numberCellBinding.buttonNumberTW.backgroundTintList =
                context.getColorStateList(R.color.mantis)
            numberCellBinding.buttonNumberTW.setTextColor(context.getColor(R.color.mantis))
            numberCellBinding.buttonNumberTW.setOnClickListener {
                viewModel.setStatus(NumberStatus.ONE_MORE, rentedNumber.numberId)
            }
        }
    }

    private fun hideItem() {
        this.itemView.visibility = View.GONE
        this.itemView.layoutParams.height = 0
    }
}