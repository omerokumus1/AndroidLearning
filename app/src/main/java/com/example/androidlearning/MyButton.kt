package com.example.androidlearning

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class MyButton @JvmOverloads constructor(
    private val context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) :
    AppCompatButton(context, attrs, defStyleAttr) {

    init {
        setOnClickListener(null)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener {
            l?.onClick(it)
            (context as? Activity)?.hideKeyboard()
        }
    }
}