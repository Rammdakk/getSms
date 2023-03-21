package com.rammdakk.getSms.ui.view.webView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.R
import com.rammdakk.getSms.databinding.FragmentWebViewBinding
import com.rammdakk.getSms.ioc.CustomWebViewClient
import com.rammdakk.getSms.ioc.WebViewLoadHandler

class WebViewFragment(private val url: String, private val loadHandler: WebViewLoadHandler) :
    Fragment() {

    companion object {
        const val TAG = "WebViewFragment"
        fun newInstance(url: String, loadHandler: WebViewLoadHandler) = WebViewFragment(url, loadHandler)
    }

    private lateinit var navigator: AppNavigator
    private lateinit var binding: FragmentWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = AppNavigator(parentFragmentManager, R.id.content_container)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)
        binding.webView.webViewClient =
            CustomWebViewClient(loadHandler = loadHandler)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)
        binding.doneTVWebView.setOnClickListener {
            navigator.back()
        }
        binding.backIVWebView.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                navigator.back()
            }
        }
        binding.forwardIVWebView.setOnClickListener {
            if (binding.webView.canGoForward()) {
                binding.webView.goForward()
            }
        }
        binding.refreshIVWebView.setOnClickListener {
            binding.webView.reload()
        }
        return binding.root
    }
}

