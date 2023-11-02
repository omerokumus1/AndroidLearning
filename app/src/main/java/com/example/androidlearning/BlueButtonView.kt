package com.example.androidlearning

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.example.androidlearning.databinding.ViewBlueButtonBinding

class BlueButtonView @JvmOverloads constructor(
    val context: Context,
    val attrs: AttributeSet? = null
) : View(context, attrs) {
    var binding: ViewBlueButtonBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewBlueButtonBinding.inflate(inflater)
    }
}