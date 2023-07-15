package com.example.androidlearning

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.androidlearning.databinding.RoundedButtonBinding


class RoundedButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    var binding: RoundedButtonBinding
    var buttonText: String?
    var buttonTextSize: Float

    companion object {
        const val DEFAULT_TEXT_SIZE = 16f
        const val DEFAULT_TEXT = "Initial Text"
    }
    init {
        val root = inflate(context, R.layout.rounded_button, this)
        binding = RoundedButtonBinding.bind(root)
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.RoundedButton, 0, 0)
        buttonText = a.getString(R.styleable.RoundedButton_buttonText)
        buttonTextSize = a.getFloat(R.styleable.RoundedButton_buttonTextSize, DEFAULT_TEXT_SIZE)
        a.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setup()
    }

    private fun setup() {
        binding.button.run {
            text = buttonText ?: DEFAULT_TEXT
            textSize = buttonTextSize
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