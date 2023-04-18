package com.rammdakk.getSms

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.rammdakk.getSms.connectvity.ConnectivityModule
import com.rammdakk.getSms.domain.push.PushBroadcastReceiver
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
        setUpAlarm()
    }

    private fun setUpAlarm() {
        val intent = Intent(baseContext, PushBroadcastReceiver::class.java)

        val pendingIntent =
            PendingIntent.getBroadcast(
                baseContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val alarmManager = baseContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            60000L,
            pendingIntent
        )
    }

    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }
}