package com.rammdakk.getSms.domain.push

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.rammdakk.getSms.R
import com.rammdakk.getSms.data.core.model.RentedNumber
import com.rammdakk.getSms.infra.castToInt
import com.rammdakk.getSms.ui.view.MainActivity
import org.jsoup.Jsoup

class PushBroadcastReceiver() : BroadcastReceiver() {
    private var getItemsForPush: GetItemsForPush? = null

    private lateinit var mBuilder: NotificationCompat.Builder
    private lateinit var mNotificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        val cookie = context.getSharedPreferences(
            "com.rammdakk.getSms", Context.MODE_PRIVATE
        )?.getString("cookies", "") ?: return
        if (getItemsForPush == null) {
            setUpPush(context)
            getItemsForPush = GetItemsForPush(mBuilder, mNotificationManager)
        }
        getItemsForPush?.execute(cookie)
    }

    private fun setUpPush(context: Context) {
        mBuilder = NotificationCompat.Builder(context, "GetSms")
        val ii = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            ii,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setSmallIcon(R.drawable.logo)
        mBuilder.priority = Notification.PRIORITY_MAX
        mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "GetSms"
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }
    }


    class GetItemsForPush(
        private var mBuilder: NotificationCompat.Builder,
        private var mNotificationManager: NotificationManager
    ) :
        AsyncTask<String, Void, List<RentedNumber>>() {

        companion object {
            private var prevListOfNumber: List<RentedNumber> = emptyList()
        }

        override fun doInBackground(vararg cookie: String): List<RentedNumber> {
            return try {
                val url =
                    "https://vak-sms.com/getNumber/"
                val doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .timeout(5000)
                    .header("cookie", cookie[0])
                    .referrer("http://google.com")
                    .header("Host", "vak-sms.com")
                    .get()
                val elements = doc.getElementsByClass("dropdown-toggle").map {
                    Jsoup.parse(it.html())
                }
                val res = elements.map { doc ->
                    val elements = doc.getElementsByAttributeValue("id", "copy")
                    val time = doc.getElementsByClass("countdown")
                    val codes = doc.getElementsByClass("codes")
                    RentedNumber(
                        serviceName = elements[0].text(),
                        numberId = elements[0].attr("rel"),
                        timeLeft = time[0].text().castToInt() ?: 0,
                        number = elements[1].attr("rel"),
                        codes = codes[0].text()
                    )
                }
                res
            } catch (ex: java.lang.Exception) {
                return emptyList()
            }
        }

        override fun onPostExecute(res: List<RentedNumber>) {
            Log.d("Casted--12", res.toString())
            Log.d("Casted--12 prev", prevListOfNumber.toString())
            val newForPush = res.filter { number ->
                !(prevListOfNumber.any { (it.numberId == number.numberId && it.codes == number.codes) }
                    ?: true)
            }
            newForPush.forEach {
                mBuilder.setContentTitle("${it.serviceName} - ${it.number.replace("+", "")}")
                mBuilder.setContentText("Код: ${it.codes}")
                mNotificationManager.notify(
                    it.numberId.hashCode(),
                    mBuilder.build()
                )
            }
            prevListOfNumber = res
            Log.d("Casted--12 prev", prevListOfNumber.toString())
        }
    }
}
