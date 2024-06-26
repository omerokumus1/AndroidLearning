package com.example.androidlearning

import android.content.Intent
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidlearning.databinding.Activity3Binding

class Activity3 : BaseActivity() {

    private lateinit var binding: Activity3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.startMainActivityBtn.setOnClickListener {
            Intent(this, MainActivity::class.java).also { startActivity(it) }
        }

        binding.startActivity2Btn.setOnClickListener {
            Intent(this, Activity2::class.java).also {
                startActivity(it)
            }
        }

        binding.startActivity3Btn.setOnClickListener {
            Intent(this, Activity3::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("New Intent on ${this.localClassName}")
        Backstack.print()
    }
}