package com.rammdakk.getSms.data.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rammdakk.getSms.data.datasource.DataSource
import com.rammdakk.getSms.data.model.Service
import com.rammdakk.getSms.ioc.ApplicationComponentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ApplicationComponentScope
class ServiceRepository @Inject constructor(
    private val dataSource: DataSource
) {

    private val _services = MutableLiveData<List<Service>>()
    val services = _services

    suspend fun loadServices() {
        Log.d("repo", "load")
        try {
            val loadedList = withContext(Dispatchers.IO) {
                dataSource.loadServices()
            }
            Log.d("Loaded", loadedList.toString())
            _services.postValue(loadedList)

        } catch (e: Exception) {
//            _error.postValue(e)
        }
    }

}
