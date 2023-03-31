package com.rammdakk.getSms.ui.view.rentedNumbers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.rammdakk.getSms.core.model.RentedNumber
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

//    override fun getItem(position: Int): RentedNumber {
//        val ll = listOf<RentedNumber>(RentedNumber("test", "23234",23,"792837243","367"))
//        Log.d("getItem", ll.toString())
//        return currentList[position]
//        return super.getItem(position)
//    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        Log.d("RentedNumberViewHolderAdapter", position.toString())
        holder.bind(getItem(position))
    }
}