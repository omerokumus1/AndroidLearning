package com.example.androidlearning

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val toast = Toast.makeText(this, "Toast message", Toast.LENGTH_SHORT)
            // Requires API 30 / Android 11 / R
            toast.addCallback(object : Toast.Callback() {
                override fun onToastShown() {
                    super.onToastShown()
                    println("Toast shown")
                }

                override fun onToastHidden() {
                    super.onToastHidden()
                    println("Toast hidden")
                }
            })

            toast.cancel()

            toast.show()
        }


    }


}
