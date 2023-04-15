package com.rammdakk.getSms.ui.view.rentedNumbers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rammdakk.getSms.R
import com.rammdakk.getSms.data.core.model.RentedNumber
import com.rammdakk.getSms.databinding.FragmentRentedNumbersBinding
import com.rammdakk.getSms.ui.stateholders.RentedNumbersViewModel
import com.rammdakk.getSms.ui.view.MainActivity

class RentedNumbersScreenController(
    private var binding: FragmentRentedNumbersBinding,
    private var lifecycleOwner: LifecycleOwner,
    private var viewModel: RentedNumbersViewModel,
    private var adapter: RentedNumberViewHolderAdapter
) {

    private lateinit var timerHandler: Handler
    private lateinit var mBuilder: NotificationCompat.Builder
    private lateinit var mNotificationManager: NotificationManager
    private var timerRunnable: Runnable? = null
    private var updateInterval = 10000L

    var myObserver: Observer<List<RentedNumber>> = object : Observer<List<RentedNumber>> {
        override fun onChanged(value: List<RentedNumber>) {
            value.forEach {
                mBuilder.setContentTitle("${it.serviceName} - ${it.number.replace("+", "")}")
                mBuilder.setContentText("Код: ${it.codes}")
                mNotificationManager.notify(
                    it.numberId.hashCode(),
                    mBuilder.build()
                )
            }
        }
    }

    fun setUpViews() {
        setUpList()
        setUpSwipeToRefresh()
        setUpAutoRefresh()
        setUpPush()
    }

    private fun setUpPush() {
        val mContext = binding.root.context
        mBuilder = NotificationCompat.Builder(mContext, "GetSms")
        val ii = Intent(mContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0)
        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setSmallIcon(R.drawable.logo)
        mBuilder.priority = Notification.PRIORITY_MAX
        mNotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "GetSms"
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
            viewModel.numbersForPush.observeForever(myObserver)
        }
    }


    private fun setUpList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = adapter
        viewModel.numbers.observe(lifecycleOwner) { numbers ->
            adapter.submitList(numbers)
            binding.swipeRefreshLayout.isRefreshing = false
            setUpAutoRefresh()
        }
        viewModel.status.observe(lifecycleOwner) {
            Log.d("Ramil", "Status update $it")
            viewModel.getActiveNumbers()
        }
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getActiveNumbers()
        }

    }

    private fun setUpAutoRefresh() {
        removeCallbacks()
        timerHandler = Handler(Looper.getMainLooper())
        timerRunnable = object : Runnable {
            override fun run() {
                removeCallbacks()
                updateInterval =
                    if (viewModel.numbers.value?.isNotEmpty() == true) 10000L else 40000L
                viewModel.getActiveNumbers()
                timerHandler.postDelayed(this, updateInterval)
            }
        }
        timerHandler.postDelayed(timerRunnable!!, updateInterval)
    }

    fun removeCallbacks() {
        timerRunnable?.let { timerHandler.removeCallbacks(it) }
    }

    fun removeObserver() {
        viewModel.numbersForPush.removeObserver(myObserver)
    }

}