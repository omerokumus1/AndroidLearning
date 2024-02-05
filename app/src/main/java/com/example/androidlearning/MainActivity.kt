package com.example.androidlearning

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bir Activity oluşturulma anında ActivityThread methodu oluşturulacak olan
        // Activity'nin attach() methodunu çağırır.
        // The attach method is called before onCreate.
        // In the attach method, a Window is created for the Activity

        // DecorView is the inner class of PhoneWindow and is the root
        // container in the View hierarchy for an Activity.
        // DecorView extends FrameLayout.

//        window.decorView.setBackgroundColor(Color.rgb(255, 0, 0))
        window.decorView.run {
            background = ColorDrawable(Color.rgb(255, 0, 0))
        }
        window.statusBarColor = Color.rgb(0, 255, 0)
        window.navigationBarColor = Color.rgb(0, 0, 255)
        (window.decorView as ViewGroup).children.run {
            Log.d("MainActivity", this.count().toString())
            forEach {
                Log.d("MainActivity", it.javaClass.name)
            }

        }


        // Such elements as Activity, Dialog, StatusBar
        // have their own Window. Each Window has its own Surface on which to render.


    }


}
