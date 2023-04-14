package com.rammdakk.getSms.ui.view.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.MainScreen
import com.rammdakk.getSms.R
import com.rammdakk.getSms.WebViewScreen
import com.rammdakk.getSms.databinding.FragmentWebViewLoginBinding
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.ioc.CustomWebViewClient
import com.rammdakk.getSms.ioc.ResultHandler
import com.rammdakk.getSms.ioc.WebViewLoadHandler
import com.rammdakk.getSms.ioc.loginScreen.ResetPSWRDWebViewLoadHandlerImpl
import com.rammdakk.getSms.ioc.loginScreen.SignInWebViewLoadHandlerImpl
import com.rammdakk.getSms.ioc.loginScreen.SignUpWebViewLoadHandlerImpl


class WebViewLoginFragment : Fragment(), ResultHandler<List<String>> {
    private lateinit var navigator: AppNavigator
    private lateinit var webView: WebView
    private lateinit var binding: FragmentWebViewLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = AppNavigator(parentFragmentManager, R.id.content_container)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewLoginBinding.inflate(layoutInflater)
        val view = binding.root
        binding.signInBtn.setOnClickListener {
            binding.warningTextView.isVisible = false
            if (binding.loginEditText.text.isNotEmpty() && binding.pswrdEditText.text.isNotEmpty()) {
                configureWebView(
                    SignInWebViewLoadHandlerImpl(
                        this,
                        binding.loginEditText.text.toString(),
                        binding.pswrdEditText.text.toString()
                    ),
                    UrlLinks.URL_LOGOUT_TO_LK
                )
            } else {
                binding.warningTextView.apply {
                    text = "Поля логин и пароль не должны быть пустые"
                    isVisible = true
                }
            }
        }
        binding.signUpBtn.setOnClickListener {
            navigator.navigateTo(
                WebViewScreen(
                    UrlLinks.URL_LOGOUT_TO_SIGN_UP,
                    SignUpWebViewLoadHandlerImpl(this)
                ), true
            )
        }
        binding.resetPswrdBtn.setOnClickListener {
            navigator.navigateTo(
                WebViewScreen(
                    UrlLinks.URL_LOGOUT_TO_RESET_PSWRD,
                    ResetPSWRDWebViewLoadHandlerImpl(this)
                ), true
            )
        }
        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView(loadHandler: WebViewLoadHandler, url: String) {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)?.hideSoftInputFromWindow(
            requireView().windowToken,
            0
        )
        webView = binding.webview
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = CustomWebViewClient(
            loadHandler
        )
        webView.loadUrl(url)
        webView.reload().apply {
            showLoading(true)
        }
    }

    private fun showLoading(showProgressBar: Boolean) {
        binding.loginEditText.isVisible = !showProgressBar
        binding.pswrdEditText.isVisible = !showProgressBar
        binding.signInBtn.isVisible = !showProgressBar
        binding.signUpBtn.isVisible = !showProgressBar
        binding.resetPswrdBtn.isVisible = !showProgressBar
        binding.progressBar.isVisible = showProgressBar
    }


    override fun onSuccess(data: List<String>) {
        val prefs = context?.getSharedPreferences(
            "com.rammdakk.getSms", Context.MODE_PRIVATE
        )
        prefs?.edit()?.putString("accessKey", data[0])?.apply()
        prefs?.edit()?.putString("cookies", data[1])?.apply()
        navigator.navigateTo(MainScreen)
    }

    override fun onError(message: String) {
        navigator.back()
        if (message.isNotEmpty()) {
            binding.warningTextView.apply {
                text = message
                isVisible = true
            }
        }
        binding.webview.webViewClient = WebViewClient()
        showLoading(false)
    }
}