package com.example.androidlearning

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //? 1. Set focus to the EditText. May not work on the emulator
        binding.editText.requestFocus()

        //? 2. Show the keyboard. May not work on the emulator
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editText, InputMethodManager.SHOW_IMPLICIT) // or SHOW_FORCED

    }
}
