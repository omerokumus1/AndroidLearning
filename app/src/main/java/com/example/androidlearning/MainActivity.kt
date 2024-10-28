package com.example.androidlearning

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // * UI Logic State Holder created in the UI
    private val userInputHandler = UserInputHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.editText.addTextChangedListener {
            // * Update state of the UI
            userInputHandler.setInputText(it.toString())
        }

        binding.button.setOnClickListener {
            // * Use UI logic state holder to validate input
            if (userInputHandler.isInputValid()) {
                Toast.makeText(this, "Input Validated", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
