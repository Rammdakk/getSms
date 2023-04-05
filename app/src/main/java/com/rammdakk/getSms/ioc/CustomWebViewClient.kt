package com.rammdakk.getSms.ioc

import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient(
    private val loadHandler: WebViewLoadHandler? = null
) :
    WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest
    ): Boolean =
        loadHandler?.redirectRules(view, request) ?: super.shouldOverrideUrlLoading(view, request)

    override fun onPageFinished(view: WebView, url: String) =
        loadHandler?.handleLoading(view, url) ?: super.onPageFinished(view, url)

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        Log.d("RMAIl", "ERRROR")
        super.onReceivedError(view, request, error)
    }
}