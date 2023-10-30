package com.example.androidlearning

import android.content.Context
import android.view.LayoutInflater
import com.example.androidlearning.databinding.ViewBlueButtonBinding

class BlueButtonView(val context: Context){
    var binding: ViewBlueButtonBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = ViewBlueButtonBinding.inflate(inflater)
    }
}