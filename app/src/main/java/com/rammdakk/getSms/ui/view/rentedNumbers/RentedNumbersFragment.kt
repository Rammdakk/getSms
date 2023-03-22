package com.rammdakk.getSms.ui.view.rentedNumbers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rammdakk.getSms.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RentedNumbersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RentedNumbersFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreate", "RentedNumbersFragment")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("onCreateView", "RentedNumbersFragment")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rented_numbers, container, false)
    }

    override fun onResume() {
        Log.d("onResume", "RentedNumbersFragment")
        super.onResume()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RentedNumbersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = RentedNumbersFragment()
    }
}