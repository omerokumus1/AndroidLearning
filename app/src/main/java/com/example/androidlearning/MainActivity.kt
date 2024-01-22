package com.example.androidlearning

import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.yLabels.let {
            val lastValue = (it.get(it.childCount - 1) as TextView).text.toString().toInt()
            it.viewTreeObserver.addOnDrawListener {
                val density = it.height.toDp().toFloat()/lastValue
                val h1 = density*4
                val h2 = density*8
                val h3 = density*5
                binding.boxRow.get(0).layoutParams.height = h1.toInt()
                binding.boxRow.get(1).layoutParams.height = h2.toInt()
                binding.boxRow.get(2).layoutParams.height = h3.toInt()
                binding.boxRow.requestLayout()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.boxRow.get(0).viewTreeObserver.addOnDrawListener {
            Log.d("charts", "measuredWidth: ${(binding.boxRow.get(0) as ImageView).measuredWidth}")
            Log.d("charts", "width as dp: ${(binding.boxRow.get(0) as ImageView).width.toDp()}")
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("charts", "measuredWidth: ${(binding.boxRow.get(0) as ImageView).measuredWidth}")
        Log.d("charts", "width as dp: ${(binding.boxRow.get(0) as ImageView).width.toDp()}")
    }


    fun Float.toDp(): Int {
        val density = resources.displayMetrics.density
        return (this/density + 0.5f).toInt()
    }

    fun Int.toDp(): Int {
        val density = resources.displayMetrics.density
        return (this/density + 0.5f).toInt()
    }


}
