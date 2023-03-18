package com.rammdakk.getSms.ioc.login

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.rammdakk.getSms.ui.login.ResultHandler
import com.rammdakk.getSms.infra.UrlLinks

class ResetPSWRDWebViewLoadHandlerImpl(private val resultHandler: ResultHandler) :
    WebViewLoadHandler {
    override fun handleLoading(webView: WebView, url: String) {
        if (webView.url == UrlLinks.URl_RESET_PASSWORD) {
            webView.evaluateJavascript(
                "(function() { return (document.getElementsByClassName('alert alert-danger')[0].innerHTML); })();"
            ) { html ->
                Log.d("ResetPSWRD", html)
                if (html == "\"\\nНовый пароль отправлен на почту\\n\"") {
                    resultHandler.onError("")
                }
            }
        }
    }

    override fun redirectRules(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        return !url.startsWith(UrlLinks.URl_RESET_PASSWORD)
    }
}