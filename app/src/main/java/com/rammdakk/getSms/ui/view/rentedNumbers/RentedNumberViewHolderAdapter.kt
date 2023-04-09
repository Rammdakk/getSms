package com.rammdakk.getSms.ui.view.rentedNumbers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.rammdakk.getSms.data.core.model.RentedNumber
import com.rammdakk.getSms.databinding.NumberCellBinding
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel

class RentedNumberViewHolderAdapter(
    private val viewModel: RentedNumbersViewModel,
    rentedNumbersDiffCalc: RentedNumbersDiffCalc,
) : ListAdapter<RentedNumber, NumberViewHolder>(rentedNumbersDiffCalc) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val binding = NumberCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        Log.d("RentedNumberViewHolderAdapter", position.toString())
        holder.bind(getItem(position))
    }
}