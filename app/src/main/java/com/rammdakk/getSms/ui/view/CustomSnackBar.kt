package com.rammdakk.getSms.ui.view

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.rammdakk.getSms.R
import com.rammdakk.getSms.databinding.WarningLayoutBinding


class CustomSnackbar(private val view: View) {

    fun showSnackBar(
        title: String,
        cancelFun: () -> Unit = {},
        @androidx.annotation.IntRange(from = -2) length: Int
    ): Snackbar {
        val snackView = View.inflate(view.context, R.layout.warning_layout, null)
        val binding = WarningLayoutBinding.bind(snackView)
        val snackbar = Snackbar.make(view, "", length)
        (snackbar.view as ViewGroup).removeAllViews()
        (snackbar.view as ViewGroup).addView(binding.root)
        snackbar.view.setPadding(0, 0, 0, 0)
        snackbar.view.elevation = 0f
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        binding.toastText.text = title
        binding.toastText.setOnClickListener {
            cancelFun()
            snackbar.dismiss()
        }
        snackbar.show()
        return snackbar
    }
}