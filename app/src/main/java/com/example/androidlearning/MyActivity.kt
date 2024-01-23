package com.example.androidlearning

import android.os.Bundle
import android.os.PersistableBundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val gestureDetector = GestureDetector(this,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    hideKeyboard()
                    return true
                }
            })
        window.decorView.setOnTouchListener { view, event ->
            gestureDetector.onTouchEvent(event)
            view.performClick()
            true
        }
    }
}