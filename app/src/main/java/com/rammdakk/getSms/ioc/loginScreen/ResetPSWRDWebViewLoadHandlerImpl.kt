package com.rammdakk.getSms.ioc.loginScreen

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.core.view.isVisible
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.ResultHandler
import com.rammdakk.getSms.ioc.WebViewLoadHandler

class ResetPSWRDWebViewLoadHandlerImpl(private val resultHandler: ResultHandler<List<String>>) :
    WebViewLoadHandler {
    override fun handleLoading(webView: WebView, url: String) {
        if (url == UrlLinks.URl_RESET_PASSWORD) {
            webView.evaluateJavascript(
                "(function() { return (document.getElementsByClassName('alert alert-danger')[0].innerHTML); })();"
            ) { html ->
                Log.d("ResetPSWRD", html)
                if (html == "\"\\nНовый пароль отправлен на почту\\n\"") {
                    resultHandler.onError("Новый пароль отправлен на почту")
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
        }
    }

    override fun redirectRules(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        return !url.startsWith(UrlLinks.URl_RESET_PASSWORD)
    }
}