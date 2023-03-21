package com.rammdakk.getSms.ui.view

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.rammdakk.getSms.R
import com.rammdakk.getSms.databinding.WarningLayoutBinding


class CustomSnackbar(private val view: View) {

    fun showSnackBar(
        message: String,
        @IntRange(from = -2) length: Int
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
        binding.errorMessage.text = message
        snackbar.show()
        return snackbar
    }
}