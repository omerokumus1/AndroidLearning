package com.example.androidlearning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.card1.customize(ColorCardViewData(255, 122, 176, 12))
        binding.card2.customize(ColorCardViewData(255, 213, 213, 99))
        binding.card3.customize(ColorCardViewData(255, 23, 255, 189))
    }


}
