package com.rammdakk.getSms.ui.login

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rammdakk.getSms.ioc.login.WebViewLoadHandler

class LoginWebViewClient(
    private val loadHandler: WebViewLoadHandler
) :
    WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest
    ): Boolean = loadHandler.redirectRules(view, request)

    override fun onPageFinished(view: WebView, url: String) = loadHandler.handleLoading(view, url)

}