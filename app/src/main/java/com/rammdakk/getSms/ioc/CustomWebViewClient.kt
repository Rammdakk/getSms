package com.rammdakk.getSms.ioc

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient(
    private val loadHandler: WebViewLoadHandler? = null
) :
    WebViewClient() {

    companion object {
        private const val RELOAD_INTERVAL = 2000L
    }

    private var wasViewReloaded = false
    override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest
    ): Boolean =
        loadHandler?.redirectRules(view, request) ?: super.shouldOverrideUrlLoading(view, request)

    override fun onPageFinished(view: WebView, url: String) =
        loadHandler?.handleLoading(view, url) ?: super.onPageFinished(view, url)

    private fun handleReceivedError(
        description: String?,
        errorCode: Int?,
        view: WebView?
    ) {
        if (errorCode == ERROR_CONNECT && !wasViewReloaded) {
            reloadWebViewOnFirstTime(view)
            return
        }
        val err = description ?: ERROR_UNKNOWN
        Log.e("CustomWebViewClient", "onReceivedError new: $err ")
    }

    private fun reloadWebViewOnFirstTime(
        view: WebView?,
    ) {
        if (view == null) return
        wasViewReloaded = true
        Handler(Looper.getMainLooper()).postDelayed({
            view.reload()
        }, RELOAD_INTERVAL)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        handleReceivedError(
            description = error?.description?.toString(),
            errorCode = error?.errorCode,
            view = view,
        )
    }
}