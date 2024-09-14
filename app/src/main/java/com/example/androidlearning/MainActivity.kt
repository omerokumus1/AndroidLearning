package com.example.androidlearning

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.androidlearning.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private val viewModel by viewModels<MainActivityViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //* Create a ViewModel the first time the system
        //* calls an activity's onCreate() method.
        //* Re-created activities receive the same MainActivityViewModel
        //* instance created by the first activity.

        //* Use the 'by viewModels()' Kotlin property delegate
        //* from the activity-ktx artifact
        val viewModel: MainActivityViewModel by viewModels()

        viewModel.uiState.observe(this) { uiState ->
            println(uiState)
        }
    }



}
