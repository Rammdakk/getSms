package com.rammdakk.getSms.ui.view.mainScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import coil.api.load
import coil.network.HttpException
import com.rammdakk.getSms.data.model.CountryInfo
import com.rammdakk.getSms.databinding.SpinnerSubitemBinding


class CountrySpinnerAdapter(
    context: Context,
    @LayoutRes res: Int,
    private var data: List<CountryInfo>
) :
    ArrayAdapter<CountryInfo>(context, res, data) {

    fun updateData(newData: List<CountryInfo>) {
        clear()
        addAll(newData)
        notifyDataSetChanged()
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            SpinnerSubitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.serviceNameTW.text = data[position].country
        binding.serviceIW.load(data[position].imageUrl) {
            this.listener(onError = { _, ex ->
                if ((ex as HttpException).response.code == 404) {
                    binding.serviceIW.load("https://vak-sms.com/static/default.png")
                }
            })
        }
        return binding.root
    }


}
