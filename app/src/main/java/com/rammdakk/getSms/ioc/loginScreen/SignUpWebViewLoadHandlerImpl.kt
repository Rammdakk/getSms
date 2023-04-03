package com.rammdakk.getSms.ioc.loginScreen

import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.core.view.isVisible
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.ResultHandler
import com.rammdakk.getSms.ioc.WebViewLoadHandler

class SignUpWebViewLoadHandlerImpl(private val resultHandler: ResultHandler<String>) :
    WebViewLoadHandler {


    override fun handleLoading(webView: WebView, url: String) {
        if (url.startsWith(UrlLinks.URL_LOGIN)) {
            resultHandler.onError("Теперь авторизуйтесь!")
        } else {
            webView.loadUrl(
                "javascript:(function() { " +
                        "document.getElementsByClassName('navbar-button')[0].style.display='none'; " +
                        "document.getElementsByClassName('navbar-collapsed')[0].style.display='none'; " +
                        " document.getElementsByClassName('col-xl-4')[0].style.display='none';})()"
            )
            webView.isVisible = true
        }
    }

    override fun redirectRules(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        return !(url.startsWith(UrlLinks.URL_SIGN_UP) || url.startsWith(UrlLinks.URL_LOGIN))
    }
}