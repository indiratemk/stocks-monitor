package com.example.stocksmonitor.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NotScrollableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

    override fun executeKeyEvent(event: KeyEvent): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return false
    }
}
