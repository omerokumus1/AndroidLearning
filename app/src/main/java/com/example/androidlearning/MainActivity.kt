package com.example.androidlearning

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        registerForContextMenu(binding.row)


        binding.row.setOnCreateContextMenuListener { menu, view, menuInfo ->
            menuInflater.inflate(R.menu.floating_context_menu, menu)

            menu.getItem(0).setOnMenuItemClickListener { item ->
                Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
                true
            }

            menu.getItem(1).setOnMenuItemClickListener { item ->
                Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show()
                true
            }

        }

        binding.row2.setOnCreateContextMenuListener { menu, view, menuInfo ->
            menuInflater.inflate(R.menu.floating_context_menu, menu)

        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v == binding.row) {
            menuInflater.inflate(R.menu.floating_context_menu, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_favorites -> {
                Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.item_share -> {
                Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


}
