package com.example.androidlearning

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // * Create ViewModel to store View state
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isInputValid.observe(this) { isValid ->
            if (isValid) {
                Toast.makeText(this, "Input is valid", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Input is invalid", Toast.LENGTH_SHORT).show()
            }
        }

        binding.editText.addTextChangedListener {
            // * Update state of the UI
            viewModel.setInputText(it.toString())
        }

        binding.button.setOnClickListener {
            // * Use UI logic state holder to validate input
            viewModel.validateInput()
        }
    }



}
