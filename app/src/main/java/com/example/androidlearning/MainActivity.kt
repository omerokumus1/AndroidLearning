package com.example.androidlearning

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ViewUtils
import androidx.core.widget.addTextChangedListener
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s == null) return
                /*if (isNotAllUppercase(s)) {
                    val uppercased = s.toString().uppercase()
                    s.replace(0, s.length, uppercased)
                }*/
                binding.textView.visibility = if (s.length > 8) View.INVISIBLE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*Log.d("MainActivity", "Text changed to ${s?.toString()}")
                Log.d("MainActivity", "start: $start")
                Log.d("MainActivity", "before: $before")
                Log.d("MainActivity", "count: $count")

                binding.button.isEnabled = (s?.length ?: 0) >= 8*/
                if (s == null) return
                binding.textView.visibility =
                    if (s.length > 8) View.INVISIBLE
                    else View.VISIBLE

            }
        }
        // binding.editText.addTextChangedListener(textWatcher)

        /*binding.editText.addTextChangedListener(
            beforeTextChanged = { s, start, count, after ->
                // Do nothing
            },
            onTextChanged = { s, start, before, count ->
                // Do nothing
            },
            afterTextChanged = { s ->
                // Do nothing
            }
        )

        */

        /*binding.editText.addTextChangedListener(
            onTextChanged = { s, start, before, count ->
                // Do nothing
            },
        )*/

        // afterTextChanged
        binding.editText.addTextChangedListener { text ->
            if (text == null) return@addTextChangedListener
            binding.textView.visibility =
                if (text.length > 8) View.INVISIBLE
                else View.VISIBLE
        }

    }

    private fun isNotAllUppercase(s: Editable): Boolean {
        s.forEach { if (it.isLowerCase()) return true }
        return false
    }


}
