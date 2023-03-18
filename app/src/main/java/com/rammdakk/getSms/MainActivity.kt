package com.rammdakk.getSms

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rammdakk.getSms.ui.login.WebViewLoginFragment
import com.rammdakk.getSms.ui.mainScreen.MainScreenFragment
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences(
            "com.rammdakk.getSms", Context.MODE_PRIVATE
        )
        Log.d("Tag12", File("").absolutePath)
        supportFragmentManager.beginTransaction().apply {
            if ((prefs.getString("accessKey", "")?.length ?: 0) > 1) {
                replace(
                    R.id.content_container,
                    MainScreenFragment.newInstance(),
                    MainScreenFragment.TAG
                )
                Log.d("Mow", "test")
            } else {
                replace(
                    R.id.content_container,
                    WebViewLoginFragment(),
                    "WebViewClient"
                )
                Log.d("Mow", "web")
            }
            setReorderingAllowed(true)
            commit()
        }
    }
}