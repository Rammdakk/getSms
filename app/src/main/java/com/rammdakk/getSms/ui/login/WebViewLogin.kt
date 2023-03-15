package com.rammdakk.getSms.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.MainScreen
import com.rammdakk.getSms.R

/**
 * A simple [Fragment] subclass.
 * Use the [WebViewLogin.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebViewLogin : Fragment() {
    private lateinit var navigator: AppNavigator
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = AppNavigator(parentFragmentManager, R.id.content_container)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_web_view_login, container, false)
        webView = view.findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                webView.evaluateJavascript(
                    "(function() { return ('<html>'+document.getElementsByClassName('sidebar')[0].outerHTML+'</html>'); })();"
                ) { html ->
                    var html = html
                    try {
                        val s = html.indexOf("data-api") + "data-api=".length + 2
                        html = html.substring(s)
                        Log.d("HTML", Integer.toString(s))
                        val str = html.substring(0, html.indexOf("\\"))
                        Log.d("HTML", str)
                        if (str.isNotBlank()) {
                            val prefs = context?.getSharedPreferences(
                                "com.rammdakk.getSms", Context.MODE_PRIVATE
                            )
                            prefs?.edit()?.putString("accessKey", str)?.apply()
                            navigator.navigateTo(MainScreen)
                        }
                    } catch (ignore: Exception) {
                        Log.d("html-ex", ignore.message!!)
                    }
                }
            }
        }

        webView.loadUrl("https://vak-sms.com/lk/")
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("WebViewLogin", "OnDestroy")
        val webViewContainer: ViewGroup? = view?.findViewById(R.id.layout_webview)
        webViewContainer?.removeView(webView).apply {
            Log.d("WebViewLogin", "OnDestroyWebview")
        }
        webView.destroy()
    }
}