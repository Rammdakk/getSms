package com.rammdakk.getSms.ui.view.webView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.rammdakk.getSms.AppNavigator
import com.rammdakk.getSms.R
import com.rammdakk.getSms.databinding.FragmentWebViewBinding
import com.rammdakk.getSms.ioc.CustomWebViewClient
import com.rammdakk.getSms.ioc.WebViewLoadHandler

class WebViewFragment() : Fragment() {
    private lateinit var url: String
    private var isVisible: Boolean = true

    companion object {
        const val TAG = "WebViewFragment"
        private lateinit var loadHandler: WebViewLoadHandler
        fun newInstance(
            url: String,
            loadHandler: WebViewLoadHandler,
            isVisible: Boolean
        ): WebViewFragment {
            this.loadHandler = loadHandler
            val bundle = Bundle()
            bundle.putString("url", url)
            bundle.putBoolean("isVisible", isVisible)
            return WebViewFragment().apply {
                this.arguments = bundle
//                it.configure(url, loadHandler, isVisible)
            }
        }

    }

    private lateinit var navigator: AppNavigator
    private lateinit var binding: FragmentWebViewBinding

//    fun configure(
//        url: String,
//        loadHandler: WebViewLoadHandler,
//        isVisible: Boolean
//    ) {
//        this.url = url
//        this.loadHandler = loadHandler
//        this.isVisible = isVisible
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = AppNavigator(parentFragmentManager, R.id.content_container)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        url = requireArguments().getString("url")!!
        isVisible = requireArguments().getBoolean("isVisible")
        binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)
        binding.webView.webViewClient =
            CustomWebViewClient(loadHandler = loadHandler)
        binding.webView.isVisible = false
        binding.webView.rootView.isVisible = this.isVisible
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

