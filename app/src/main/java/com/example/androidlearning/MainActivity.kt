package com.example.androidlearning

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var status = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val state = isAirplaneMode()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Settings.System.putInt(
                    contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON,
                    if (state) 0 else 1)

                val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            } else {
                Settings.System.putInt(
                    contentResolver,
                    Settings.System.AIRPLANE_MODE_ON,
                    if (state) 0 else 1)
                val intent = Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED)
                intent.putExtra("state", !state)
                sendBroadcast(intent)
            }
        }

    }

    private fun isAirplaneMode(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) == 1
        } else {
            Settings.System.getInt(contentResolver, Settings.System.AIRPLANE_MODE_ON, 0) == 1
        }
    }


}
