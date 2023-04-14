package com.rammdakk.getSms

import android.app.Application
import android.content.Context
import com.rammdakk.getSms.connectvity.ConnectivityModule
import com.rammdakk.getSms.ioc.ApplicationComponent
import com.rammdakk.getSms.ioc.DaggerApplicationComponent

class App : Application() {
    val applicationComponent: ApplicationComponent =
        DaggerApplicationComponent.builder()
            .connectivityModule(
                ConnectivityModule(this)
            ).build()


    override fun onCreate() {
        applicationComponent.injectTo(this)
        super.onCreate()
    }

    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }
}