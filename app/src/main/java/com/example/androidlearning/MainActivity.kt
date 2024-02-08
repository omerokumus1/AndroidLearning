package com.example.androidlearning

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.SubMenuBuilder
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu?.addSubMenu("Submenu")?.also {
            it.add(1, Menu.NONE, Menu.NONE, "Item 1.1")
            it.add(1, Menu.NONE, Menu.NONE, "Item 1.2")
            it.add(2, Menu.NONE, Menu.NONE, "Item 2.1")
            it.add(2, Menu.NONE, Menu.NONE, "Item 2.2")
        }

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)

        return true
    }

}
