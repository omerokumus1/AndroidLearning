package com.example.androidlearning

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.androidlearning.databinding.RoundedButtonBinding

class RoundedButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    var binding: RoundedButtonBinding

    init {
        val root = inflate(context, R.layout.rounded_button, this)
        binding = RoundedButtonBinding.bind(root)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setup()
    }

    private fun setup() {
        binding.button.run {
            text = "Initial Text"
            textSize = 16f
        }
    }

    fun customize(viewData: ViewData) {
        binding.button.run {
            text = viewData.text
            textSize = viewData.textSize
        }
    }

    data class ViewData(val text: String?, val textSize: Float)
}