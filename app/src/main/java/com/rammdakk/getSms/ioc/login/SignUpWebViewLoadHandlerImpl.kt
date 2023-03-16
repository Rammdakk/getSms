package com.rammdakk.getSms.ioc.login

import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.rammdakk.getSms.ui.login.ResultHandler
import com.rammdakk.getSms.Infra.UrlLinks

class SignUpWebViewLoadHandlerImpl(private val resultHandler: ResultHandler) : WebViewLoadHandler {


    override fun handleLoading(webView: WebView, url: String) {
        if (url.startsWith(UrlLinks.URL_LOGIN)) {
            resultHandler.onError("Теперь авторизуйтесь!")
        }
    }

    override fun redirectRules(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        return !(url.startsWith(UrlLinks.URL_SIGN_UP) || url.startsWith(UrlLinks.URL_LOGIN))
    }
}