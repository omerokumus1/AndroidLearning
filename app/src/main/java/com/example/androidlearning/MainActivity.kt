package com.example.androidlearning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val blueButton = BlueButtonViewWrapper(this)
        blueButton.binding.btn.text = "New Text"
        val set = ConstraintSet()

        binding.root.addView(blueButton.binding.root)
    }

}
