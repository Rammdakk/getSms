package com.rammdakk.getSms.ui.view.rentedNumbers.cutomView

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.rammdakk.getSms.R

class TimerTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val CRITICAL_VALUE_TIME = 30
    }

    private val textView: TextView
    private var time: Int = 1200
    private lateinit var timerHandler: Handler
    private lateinit var timerRunnable: Runnable
    private var func: (() -> Unit)? = null

    init {
        inflate(context, R.layout.timer_view, this)
        textView = findViewById(R.id.timeTW)
    }

    override fun onAttachedToWindow() {
        timerHandler = Handler(Looper.getMainLooper())
        timerRunnable = object : Runnable {
            override fun run() {
                if (time < 0) {
                    func?.let { it() }
                    timerHandler.removeCallbacks(timerRunnable)
                    return
                }
                time--
                setTextViewTime(time)
                timerHandler.postDelayed(this, 1000)
            }
        }
        super.onAttachedToWindow()
        timerHandler.postDelayed(timerRunnable, 1000)
    }

    override fun onDetachedFromWindow() {
        timerHandler.removeCallbacks(timerRunnable)
        super.onDetachedFromWindow()
    }

    fun setTimer(time: Int, func: () -> Unit) {
        this.time = time
        this.func = func
        val minutes = (time % 3600) / 60
        val seconds = time % 60
        textView.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun setTextViewTime(time: Int) {
        val minutes = (time % 3600) / 60
        val seconds = time % 60
        textView.text = String.format("%02d:%02d", minutes, seconds)
        if (time < CRITICAL_VALUE_TIME) {
            textView.backgroundTintList =
                context.getColorStateList(R.color.bittersweet)
            textView.setTextColor(context.getColor(R.color.bittersweet))
        } else {
            textView.backgroundTintList =
                context.getColorStateList(R.color.mantis)
            textView.setTextColor(context.getColor(R.color.mantis))
        }
    }

}