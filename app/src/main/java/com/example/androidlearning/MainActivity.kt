package com.example.androidlearning

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.androidlearning.databinding.ActivityMainBinding
import com.example.androidlearning.databinding.ViewBlueButtonBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val blueButton = BlueButtonView(this)
        blueButton.binding.btn.text = "New Text"
        val set = ConstraintSet()

        binding.root.addView(blueButton.binding.root)
    }

}
