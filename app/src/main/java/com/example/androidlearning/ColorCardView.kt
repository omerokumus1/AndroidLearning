package com.example.androidlearning

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.random.Random

//             val binding = ColorCardViewBinding.inflate(LayoutInflater.from(context))

class ColorCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.color_card_view, this, true)
        inflate(context, R.layout.color_card_view, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setup()
    }

    private fun setup() {
        val colorCard: ImageView = findViewById(R.id.color_image)
        val colorCode: TextView = findViewById(R.id.color_code)
        val r = Random.nextInt(256)
        val g = Random.nextInt(256)
        val b = Random.nextInt(256)
        val color = Color.argb(255, r, g, b)
        val code = "#${Integer.toHexString(r)}${Integer.toHexString(g)}${Integer.toHexString(b)}"
        colorCard.setBackgroundColor(color)
        colorCode.text = code
    }
}
