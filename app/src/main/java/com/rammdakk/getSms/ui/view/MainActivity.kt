package com.rammdakk.getSms.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.rammdakk.getSms.App
import com.rammdakk.getSms.R
import com.rammdakk.getSms.SingleLiveEvent
import com.rammdakk.getSms.data.api.Result
import com.rammdakk.getSms.data.api.error.ErrorMessageConverterImpl
import com.rammdakk.getSms.data.api.error.ErrorType
import com.rammdakk.getSms.ui.stateholders.ActivityViewModel
import com.rammdakk.getSms.ui.view.login.WebViewLoginFragment
import com.rammdakk.getSms.ui.view.mainScreen.MainScreenFragment


class MainActivity : AppCompatActivity() {
    private val applicationComponent
        get() = App.get(applicationContext).applicationComponent
    private val viewModel: ActivityViewModel by viewModels { applicationComponent.getViewModelFactory() }

    val error: SingleLiveEvent<Result.Error<String>?> =
        SingleLiveEvent()

    private var snackbar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences(
            "com.rammdakk.getSms", Context.MODE_PRIVATE
        )
        launchScreen(prefs)

        viewModel.errors.observe(this) {
            error.postValue(it)
        }
        error.observe(this) {
            handleError(it)
        }
    }

    private fun handleError(error: Result.Error<String>?) {
        if (error == null) {
            snackbar?.dismiss()
            return
        }
        snackbar = CustomSnackbar(findViewById(R.id.content_container)).showSnackBar(
            ErrorMessageConverterImpl(
                resources
            ).getError(error.type, error.details ?: ""),
            if (error.type == ErrorType.Network) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
        )
    }

    private fun launchScreen(sharedPreferences: SharedPreferences) {
        supportFragmentManager.beginTransaction().apply {
            if ((sharedPreferences.getString("accessKey", "")?.length ?: 0) > 1) {
                replace(
                    R.id.content_container,
                    MainScreenFragment.newInstance(),
                    MainScreenFragment.TAG
                )
            } else {
                replace(
                    R.id.content_container,
                    WebViewLoginFragment(),
                    "WebViewClient"
                )
            }
            setReorderingAllowed(true)
            commit()
        }
    }

}