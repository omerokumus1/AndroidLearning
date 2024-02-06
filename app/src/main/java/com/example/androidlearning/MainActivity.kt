package com.example.androidlearning

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView = binding.textView
        val paint = textView.paint
        val width = paint.measureText(textView.text.toString())

        val textShader: Shader = LinearGradient(
            0f, 0f, width, textView.textSize, intArrayOf(
                Color.parseColor("#F97C3C"),
                Color.parseColor("#FDB54E"),
                Color.parseColor("#64B678"),
                Color.parseColor("#478AEA"),
                Color.parseColor("#8446CC")
            ), null, Shader.TileMode.CLAMP
        )
        textView.paint.setShader(textShader)


    }



}
