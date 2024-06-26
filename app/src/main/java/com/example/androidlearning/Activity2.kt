package com.example.androidlearning

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidlearning.databinding.Activity2Binding

class Activity2 : BaseActivity() {

    private lateinit var binding: Activity2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity2Binding.inflate(layoutInflater)
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
}