package com.example.androidlearning

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.set
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val width = 100
        val height = 100
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        if (bitmap.isMutable) {
//            repeat(width) { x ->
//                repeat(height) { y ->
//                //  bitmap.setPixel(x, y, Color.BLACK)
//                    bitmap[x, y] = Color.BLACK
//                }
//            }

            bitmap.setPixels(
                intArrayOf(
                    Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                    Color.CYAN, Color.MAGENTA, Color.LTGRAY, Color.DKGRAY
                ), 0, 2, 0, 0, 2, 10
            )

        }

        binding.imageView.setImageBitmap(bitmap)

    }


}
