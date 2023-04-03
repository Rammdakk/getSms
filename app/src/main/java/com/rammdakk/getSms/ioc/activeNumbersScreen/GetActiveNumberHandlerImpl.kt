package com.rammdakk.getSms.ioc.activeNumbersScreen

import android.util.Log
import android.webkit.WebView
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.rammdakk.getSms.core.model.RentedNumber
import com.rammdakk.getSms.ioc.ResultHandler
import com.rammdakk.getSms.ioc.WebViewLoadHandler


class GetActiveNumberHandlerImpl(private val resultHandler: ResultHandler<List<RentedNumber>>) :
    WebViewLoadHandler {
    override fun handleLoading(webView: WebView, url: String) {
        if (webView.url == "https://vak-sms.com/getNumber/") {
            webView.evaluateJavascript(
                "(function() {    " +
                        "            const elements =  document.querySelectorAll('[id=copy]');" +
                        "            const time = document.getElementsByClassName('countdown');" +
                        "            const codes = document.getElementsByClassName('codes');" +
                        "            let result = '[';" +
                        "            for (let i = 0; i < elements.length; i++) {" +
                        "                if (i % 3 !== 2) {" +
                        "                    result += `{ \"name\": \"$" + "{elements[i].textContent}\",`;" +
                        "                    result += \" \";" +
                        "                    result += `\"id\": \"$" + "{elements[i].getAttribute('rel')}\",`;" +
                        "                    result += \" \";" +
                        "                    result += `\"time\": $" + "{time[i/3].textContent.replace('\\n', \"\")},`;" +
                        "                    result += \" \";" +
                        "                    result += `\"code\": \"$" + "{codes[i/3].textContent.replace('\\n', \"\")}\",`;" +
                        "                    result += \" \";" +
                        "                    i++;" +
                        "                    result += `\"number\": \"$" + "{elements[i].getAttribute('rel')}\"`;" +
                        "                    result += \"},\";" +
                        "                }" +
                        "            }" +
                        "            return result+']';" +
                        "})();"
            ) { almostJSON ->
                Log.d("loaded", "test")
                if (almostJSON == null) {
                    Log.d("smsNumbers", "null")
                    resultHandler.onSuccess(mutableListOf())
//                    return@evaluateJavascript
                }
                try {
                    Log.d("html", almostJSON)
                    val json =
                        almostJSON.replace("\"[", "[").replace("]\"", "]").replace("\\\"", "\"")
                    Log.d("smsNumbers", "Stage 1 - ${almostJSON.replace("\"[", "[")}")
                    Log.d(
                        "smsNumbers",
                        "Stage 1 - ${almostJSON.replace("\"[", "[").replace("]\"", "]")}"
                    )
                    Log.d("smsNumbers", json)
                    val listType = object : TypeToken<List<RentedNumber>>() {}.type
                    val list: List<RentedNumber> = Gson().fromJson(json, listType)
                    Log.d("smsNumbers2", list.toString())
                    resultHandler.onSuccess(list)
//                    return@evaluateJavascript
                } catch (ex: JsonSyntaxException) {
                    resultHandler.onError(ex.message ?: "Неизвестная ошибка")
                }
            }
        }
    }
}