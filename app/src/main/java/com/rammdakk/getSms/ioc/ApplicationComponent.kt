package com.rammdakk.getSms.ioc

import com.rammdakk.getSms.App
import com.rammdakk.getSms.connectvity.ConnectivityModule
import com.rammdakk.getSms.data.api.RetrofitModule
import java.io.InputStream


@ApplicationComponentScope
@dagger.Component(modules = [RetrofitModule::class, ConnectivityModule::class])
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun injectTo(application: App)
}
