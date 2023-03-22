package com.rammdakk.getSms.ioc.login

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.WebViewLoadHandler
import com.rammdakk.getSms.ui.view.login.ResultHandler

class SignInWebViewLoadHandlerImpl(
    private val resultHandler: ResultHandler,
    private val login: String,
    private val password: String
) : WebViewLoadHandler {

    override fun redirectRules(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        return !(url.startsWith(UrlLinks.URL_LK) || url.startsWith(UrlLinks.URL_LOGIN))
    }

    override fun handleLoading(webView: WebView, url: String) {
        Log.d("Loading", url)
        if (webView.url == UrlLinks.URL_LK) {
            webView.evaluateJavascript(
                "(function() { return (document.getElementsByClassName('sidebar')[0].getAttribute('data-api')); })();"
            ) { html ->
                if (webView.url != UrlLinks.URL_LK) return@evaluateJavascript
                try {
                    if (html.isNotBlank()) {
                        resultHandler.onSuccess(html.replace("\"", ""))
                    }
                } catch (ignore: Exception) {
                    Log.d("html-ex", ignore.message!!)
                }
            }
        } else {
            webView.evaluateJavascript(
                "(function() {(document.getElementsByName('username')[0].value = '${login}'); (document.getElementsByName('password')[0].value = '${password}" +
                        "'); (document.getElementsByClassName('btn btn-success btn-fill center-block')[0].click()); return (document.getElementsByClassName('alert alert-warning')[0].textContent);})();"
            ) { html ->
                if (webView.url != UrlLinks.URL_LOGIN) return@evaluateJavascript
                try {
                    Log.d("HTML-warning", html)
                    if (!html.isNullOrBlank() && html != "null") {
                        webView.stopLoading().apply {
                            Log.d("HTML-warning", "stopped")
                        }
                        resultHandler.onError(
                            html.replace("\\n", "").replace("\"", "")
                        )
                    } else {
                        webView.evaluateJavascript(
                            "(function() { return (document.getElementsByClassName(' text-gray-600 leading-1.3 text-3xl lg:text-2xl font-light')[0].innerHTML); })();"
                        ) { html ->
                            if (!html.isNullOrBlank() && html != "null") {
                                resultHandler.onError(
                                    html
                                )
                            }
                        }
                    }
                } catch (ignore: Exception) {
                    resultHandler.onError(
                        "Произошла неизвестная ошибка"
                    )
                    Log.d("html-ex", ignore.message!!)
                }
            }
        }

    }
}