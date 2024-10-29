package com.example.androidlearning

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // * UI Logic State Holder created in the UI
    private val userInputHandler = UserInputHandler()

    // * Create ViewModel to store View state
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.editText.addTextChangedListener {
            // * Update state of the UI
//            userInputHandler.setInputText(it.toString())
            viewModel.setInputText(it.toString())
        }

        binding.button.setOnClickListener {
            // * Use UI logic state holder to validate input
            userInputHandler.setInputText(viewModel.inputText)
            if (userInputHandler.isInputValid()) {
                Toast.makeText(this, "Input Validated", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
