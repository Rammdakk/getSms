package com.rammdakk.getSms.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rammdakk.getSms.R
import com.rammdakk.getSms.ui.view.login.WebViewLoginFragment
import com.rammdakk.getSms.ui.view.mainScreen.MainScreenFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences(
            "com.rammdakk.getSms", Context.MODE_PRIVATE
        )
        launchScreen(prefs)
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