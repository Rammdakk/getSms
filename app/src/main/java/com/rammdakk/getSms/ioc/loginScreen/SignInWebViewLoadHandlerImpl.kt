package com.rammdakk.getSms.ioc.loginScreen

import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.ResultHandler
import com.rammdakk.getSms.ioc.WebViewLoadHandler

class SignInWebViewLoadHandlerImpl(
    private val resultHandler: ResultHandler<String>,
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
            val cookies: String = CookieManager.getInstance().getCookie(url)
            Log.d("Ramil Cookie", "All the cookies in a string:$cookies")
            webView.evaluateJavascript(
                "(function() { return (document.getElementsByClassName('sidebar')[0].getAttribute('data-api')); })();"
            ) { html ->
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
                        ) { s ->
                            if (!s.isNullOrBlank() && s != "null") {
                                resultHandler.onError(s)
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