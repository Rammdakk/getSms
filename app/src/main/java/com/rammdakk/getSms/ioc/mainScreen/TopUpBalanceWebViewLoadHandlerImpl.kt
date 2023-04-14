package com.rammdakk.getSms.ioc.mainScreen

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.core.view.isVisible
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.WebViewLoadHandler

class TopUpBalanceWebViewLoadHandlerImpl(
) : WebViewLoadHandler {

    override fun redirectRules(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        Log.d("URL-Balance", url)
        if (url.startsWith(UrlLinks.URL_BASE)
            && !url.startsWith(UrlLinks.URL_BALANCE)
        )
            return true
        return false
    }

    override fun handleLoading(webView: WebView, url: String) {
        webView.loadUrl(
            "javascript:(function() { " +
                    "document.getElementsByClassName('navbar-button')[0].style.display='none'; " +
                    "document.getElementsByClassName('navbar-collapsed')[0].style.display='none'; " +
                    "document.getElementsByClassName('btn btn-transparent')[0].style.display='none'; " +
                    " document.getElementsByClassName('active-number')[0].style.display='none';" +
                    " document.getElementsByClassName('list-number active')[0].style.display='none';})()"
        )
        webView.isVisible = true
    }
}