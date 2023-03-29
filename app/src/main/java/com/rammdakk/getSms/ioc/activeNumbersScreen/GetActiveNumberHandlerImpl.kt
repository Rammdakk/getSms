package com.rammdakk.getSms.ioc.activeNumbersScreen

import android.util.Log
import android.webkit.WebView
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.rammdakk.getSms.core.model.RentedNumber
import com.rammdakk.getSms.ioc.WebViewLoadHandler
import com.rammdakk.getSms.ui.view.login.ResultHandler

class GetActiveNumberHandlerImpl(private val resultHandler: ResultHandler<List<RentedNumber>>) :
    WebViewLoadHandler {
    override fun handleLoading(webView: WebView, url: String) {
        if (webView.url == "https://vak-sms.com/getNumber/") {
            webView.evaluateJavascript(
                "(function() {    " +
                        "            const elements =  document.querySelectorAll('[id=copy]');" +
                        "            const time = document.getElementsByClassName('countdown');" +
                        "            let result = '[';" +
                        "            for (let i = 0; i < elements.length; i++) {" +
                        "                if (i % 3 !== 2) {" +
                        "                    result += `{ \"name\": \"$" + "{elements[i].textContent}\",`;" +
                        "                    result += \" \";" +
                        "                    result += `\"id\": \"$" + "{elements[i].getAttribute('rel')}\",`;" +
                        "                    result += \" \";" +
                        "                    result += `\"time\": $" + "{time[i/3].textContent.replace('\\n', \"\")},`;" +
                        "                    result += \" \";" +
                        "                    i++;" +
                        "                    result += `\"number\": \"$" + "{elements[i].getAttribute('rel')}\"`;" +
                        "                    result += \"},\";" +
                        "                }" +
                        "            }" +
                        "            return result+']';" +
                        "})();"
            ) { almostJSON ->
                if (almostJSON == null) {
                    Log.d("smsNumbers", "null")
                    resultHandler.onSuccess(mutableListOf())
                    return@evaluateJavascript
                }
                try {
                    val json =
                        almostJSON.replace("\\\"", "\"").replace("\"[", "[").replace(",]\"", "]")
                    Log.d("smsNumbers", json)
                    var list: List<RentedNumber> = mutableListOf()
                    list = Gson().fromJson(json, list::class.java)
                    Log.d("smsNumbers2", list.toString())
                    resultHandler.onSuccess(list)
                    return@evaluateJavascript
                } catch (ex: JsonSyntaxException) {
                    resultHandler.onError(ex.message ?: "Неизвестная ошибка")
                }
            }
        }
    }
}