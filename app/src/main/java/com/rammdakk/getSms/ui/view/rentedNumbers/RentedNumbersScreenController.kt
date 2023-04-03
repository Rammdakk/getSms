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
import androidx.recyclerview.widget.LinearLayoutManager
import com.rammdakk.getSms.R
import com.rammdakk.getSms.core.model.RentedNumber
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
    private var timerRunnable: Runnable? = null
    private var updateInterval = 60000L

    fun setUpViews() {
        setUpList()
        setUpSwipeToRefresh()
        setUpAutoRefresh()
        setUpPush()
    }

    private fun Push(it: RentedNumber) {
        val mContext = binding.root.context

        val mBuilder = NotificationCompat.Builder(mContext, "notify_001")
        val ii = Intent(mContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0)

        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setSmallIcon(R.drawable.search_icon)
        mBuilder.priority = Notification.PRIORITY_MAX
        val mNotificationManager: NotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "Your_channel_id"
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }
        mBuilder.setContentTitle(it.number)
        mBuilder.setContentText(it.codes)
        mNotificationManager.notify(0, mBuilder.build())

        Log.d("PUSH22", "push")
    }


    private fun setUpPush() {
        val mContext = binding.root.context

        val mBuilder = NotificationCompat.Builder(mContext, "notify_001")
        val ii = Intent(mContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0)

        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setSmallIcon(R.drawable.search_icon)
        mBuilder.priority = Notification.PRIORITY_MAX
        val mNotificationManager: NotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "Your_channel_id"
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }
        viewModel.numbersForPush.observe(lifecycleOwner) { list ->
            list.forEach {
                mBuilder.setContentTitle(it.number)
                mBuilder.setContentText(it.codes)
                mNotificationManager.notify(0, mBuilder.build())
            }
            Log.d("PUSH", "push")
        }
    }


    private fun setUpList() {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.recyclerView.adapter = adapter
        viewModel.numbers.observe(lifecycleOwner) { numbers ->
            updateInterval = if (numbers.isEmpty()) 60000L else 15000L
            adapter.submitList(numbers)
            if (numbers.isNotEmpty()) {
                Log.d("Ramil", "Push called")
                Push(numbers[0])
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.status.observe(lifecycleOwner) {
            Log.d("Ramil", "Status update $it")
            binding.ww.reload()
        }
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.ww.reload()
        }
    }

    private fun setUpAutoRefresh() {
        removeCallbacks()
        timerHandler = Handler(Looper.getMainLooper())
        timerRunnable = object : Runnable {
            override fun run() {
                Log.d("setUpAutoRefresh", "Runnable ${binding.root.context}")
                setUpPush()
                binding.ww.reload()
                timerHandler.postDelayed(this, updateInterval)
            }
        }
        timerHandler.postDelayed(timerRunnable!!, updateInterval)
    }

    fun removeCallbacks() {
        timerRunnable?.let { timerHandler.removeCallbacks(it) }
    }

}