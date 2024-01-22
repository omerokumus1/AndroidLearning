package com.example.androidlearning

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {

            val snackbar = Snackbar
                .make(binding.root, "Hello World", Snackbar.LENGTH_LONG)

            snackbar.setAction("OK") {
                snackbar.dismiss()
            }
            snackbar.setActionTextColor(resources.getColor(R.color.purple_200))

            snackbar.setBackgroundTint(resources.getColor(R.color.purple_500))
            snackbar.setTextColor(resources.getColor(R.color.teal_200))
            snackbar.setTextMaxLines(1)

            snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                }

                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)
                }
            })

            snackbar.dismiss()



            snackbar.show()


        }


    }


}
