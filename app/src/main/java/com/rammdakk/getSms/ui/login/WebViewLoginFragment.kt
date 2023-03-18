package com.rammdakk.getSms.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.infra.UrlLinks
import com.rammdakk.getSms.MainScreen
import com.rammdakk.getSms.R
import com.rammdakk.getSms.databinding.FragmentWebViewLoginBinding
import com.rammdakk.getSms.ioc.login.ResetPSWRDWebViewLoadHandlerImpl
import com.rammdakk.getSms.ioc.login.SignInWebViewLoadHandlerImpl
import com.rammdakk.getSms.ioc.login.SignUpWebViewLoadHandlerImpl
import com.rammdakk.getSms.ioc.login.WebViewLoadHandler

interface ResultHandler {
    fun onSuccess(string: String)
    fun onError(string: String)
}

class WebViewLoginFragment : Fragment(), ResultHandler {
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
                    UrlLinks.URL_LK
                )
            } else {
                binding.warningTextView.apply {
                    text = "Поля логин и пароль не должны быть пустые"
                    isVisible = true
                }
            }
        }
        binding.signUpBtn.setOnClickListener {
            binding.loginConstraint.isVisible = false
            binding.webViewContainer.isVisible = true
            configureWebView(
                SignUpWebViewLoadHandlerImpl(
                    this
                ),
                UrlLinks.URL_SIGN_UP
            )
        }
        binding.resetPswrdBtn.setOnClickListener {
            binding.loginConstraint.isVisible = false
            binding.webViewContainer.isVisible = true
            configureWebView(
                ResetPSWRDWebViewLoadHandlerImpl(
                    this
                ),
                UrlLinks.URl_RESET_PASSWORD
            )
        }
        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView(loadHandler: WebViewLoadHandler, url: String) {
        webView = binding.webview
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = LoginWebViewClient(
            loadHandler
        )
        webView.loadUrl(url).apply {
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


    override fun onSuccess(string: String) {
        val prefs = context?.getSharedPreferences(
            "com.rammdakk.getSms", Context.MODE_PRIVATE
        )
        prefs?.edit()?.putString("accessKey", string)?.apply()
        navigator.navigateTo(MainScreen)
    }

    override fun onError(string: String) {
        binding.loginConstraint.isVisible = true
        binding.webViewContainer.isVisible = false
        if (string.isNotEmpty()) {
            binding.warningTextView.apply {
                text = string
                isVisible = true
            }
        }
        binding.webview.webViewClient = WebViewClient()
        showLoading(false)
    }
}