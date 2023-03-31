package com.rammdakk.getSms.ioc.login

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.ResultHandler
import com.rammdakk.getSms.ioc.WebViewLoadHandler

class ResetPSWRDWebViewLoadHandlerImpl(private val resultHandler: ResultHandler<String>) :
    WebViewLoadHandler {
    override fun handleLoading(webView: WebView, url: String) {
        if (webView.url == UrlLinks.URl_RESET_PASSWORD) {
            webView.evaluateJavascript(
                "(function() { return (document.getElementsByClassName('alert alert-danger')[0].innerHTML); })();"
            ) { html ->
                Log.d("ResetPSWRD", html)
                if (html == "\"\\nНовый пароль отправлен на почту\\n\"") {
                    resultHandler.onError("Новый пароль отправлен на почту")
                }
            }
        }
    }

    override fun redirectRules(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        return !url.startsWith(UrlLinks.URl_RESET_PASSWORD)
    }
}