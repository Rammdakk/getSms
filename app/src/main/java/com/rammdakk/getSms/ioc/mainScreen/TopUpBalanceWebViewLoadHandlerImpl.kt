package com.rammdakk.getSms.ioc.mainScreen

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.rammdakk.getSms.ui.view.login.ResultHandler
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.WebViewLoadHandler

class TopUpBalanceWebViewLoadHandlerImpl(
) : WebViewLoadHandler {

    override fun redirectRules(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        Log.d("URL-Balance", url)
        if (url.startsWith("https://vak-sms.com")
            && !url.startsWith("https://vak-sms.com/pay/")
        )
            return true
        return false
    }

    override fun handleLoading(webView: WebView, url: String) {
    }
}