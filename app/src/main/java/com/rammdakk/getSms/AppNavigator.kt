package com.rammdakk.getSms

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.rammdakk.getSms.ioc.WebViewLoadHandler
import com.rammdakk.getSms.ui.view.login.WebViewLoginFragment
import com.rammdakk.getSms.ui.view.mainScreen.MainScreenFragment
import com.rammdakk.getSms.ui.view.webView.WebViewFragment

interface Screen {
    val addToBackStack: Boolean
        get() = true

    fun createFragment(): Fragment
}

object MainScreen : Screen {
    override fun createFragment(): Fragment = MainScreenFragment.newInstance()
    override val addToBackStack: Boolean
        get() = false
}

object LogInScreen : Screen {
    override fun createFragment(): Fragment = WebViewLoginFragment()
    override val addToBackStack: Boolean
        get() = false
}

class WebViewScreen(private val url: String, private val loadHandler: WebViewLoadHandler) : Screen {
    override fun createFragment(): Fragment = WebViewFragment.newInstance(url, loadHandler)
    override val addToBackStack: Boolean
        get() = true
}

class AppNavigator(
    var fragmentManager: FragmentManager?,
    @IdRes private val containerId: Int
) {
    fun navigateTo(screen: Screen) {
        fragmentManager?.apply {
            beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right,
                )
                replace(containerId, screen.createFragment())
                setReorderingAllowed(true)
                if (screen.addToBackStack) {
                    addToBackStack(null)
                }
                commit()
            }
        }
    }

    fun back() {
        if ((fragmentManager?.backStackEntryCount ?: 0) > 0) {
            fragmentManager?.popBackStack();
        }
    }
}