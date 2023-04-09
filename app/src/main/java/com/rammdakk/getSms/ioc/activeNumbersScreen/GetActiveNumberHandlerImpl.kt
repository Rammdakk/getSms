package com.rammdakk.getSms.ioc.activeNumbersScreen

import android.util.Log
import android.webkit.WebView
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.rammdakk.getSms.data.core.model.RentedNumber
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.ResultHandler
import com.rammdakk.getSms.ioc.WebViewLoadHandler


class GetActiveNumberHandlerImpl(private val resultHandler: ResultHandler<List<RentedNumber>>) :
    WebViewLoadHandler {
    override fun handleLoading(webView: WebView, url: String) {
        if (webView.url == UrlLinks.URL_RENTED_LIST) {
 //       if (webView.url == "http://192.168.1.109:8080/test/html_with_codes") {
            webView.evaluateJavascript(
                "(function() {    " +
                        "            let result = '[';" +
                        "            const dropdownHovers = document.getElementsByClassName('dropdown-hover');" +
                        "            for (let dropdownHover of dropdownHovers){" +
                        "                   const elements =  dropdownHover.querySelectorAll('[id=copy]');" +
                        "                   const time = dropdownHover.getElementsByClassName('countdown');" +
                        "                   const codes = dropdownHover.getElementsByClassName('codes');" +
                        "                    result += `{ \"name\": \"$" + "{elements[0].textContent}\",`;" +
                        "                    result += \" \";" +
                        "                    result += `\"id\": \"$" + "{elements[0].getAttribute('rel')}\",`;" +
                        "                    result += \" \";" +
                        "                    result += `\"time\": $" + "{time[0].textContent.replace('\\n', \"\")},`;" +
                        "                    result += \" \";" +
                        "                    result += `\"code\": \"$" + "{codes[0].textContent.replace('\\n', \"\")}\",`;" +
                        "                    result += \" \";" +
                        "                    result += `\"number\": \"$" + "{elements[1].getAttribute('rel')}\"`;" +
                        "                    result += \"},\";" +
                        "            }" +
                        "            return result+']';" +
                        "})();"
            ) { almostJSON ->
                Log.d("loaded", almostJSON)
                if (almostJSON == null || almostJSON == "null") {
                    Log.d("smsNumbers", "null")
                    resultHandler.onSuccess(mutableListOf())
                    return@evaluateJavascript
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
                } catch (ex: JsonSyntaxException) {
                    resultHandler.onError(ex.message ?: "Неизвестная ошибка")
                }
            }
        }
    }
}