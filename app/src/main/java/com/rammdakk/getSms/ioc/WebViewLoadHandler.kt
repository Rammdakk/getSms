package com.rammdakk.getSms.ioc

import android.webkit.WebResourceRequest
import android.webkit.WebView

interface WebViewLoadHandler {
    fun handleLoading(webView: WebView, url: String)
    fun redirectRules(
        view: WebView,
        request: WebResourceRequest
    ): Boolean = false
}
