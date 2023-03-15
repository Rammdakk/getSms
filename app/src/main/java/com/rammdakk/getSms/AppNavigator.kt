package com.rammdakk.getSms

import android.view.View
import android.webkit.WebView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.rammdakk.getSms.ui.mainScreen.MainScreenFragment

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
}