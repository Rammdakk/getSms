package com.rammdakk.getSms.ioc

import com.rammdakk.getSms.App
import com.rammdakk.getSms.connectvity.ConnectivityModule
import com.rammdakk.getSms.data.net.api.vakSms.VakSmsRetrofitModule


@ApplicationComponentScope
@dagger.Component(modules = [VakSmsRetrofitModule::class, ConnectivityModule::class])
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun injectTo(application: App)
}
