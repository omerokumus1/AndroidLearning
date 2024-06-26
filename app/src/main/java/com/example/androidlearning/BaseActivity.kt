package com.example.androidlearning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Backstack.push(this.localClassName)
        title = this.localClassName

    }

    override fun onDestroy() {
        super.onDestroy()
        Backstack.pop()
    }
}