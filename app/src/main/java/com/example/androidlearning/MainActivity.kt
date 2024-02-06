package com.example.androidlearning

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding
import java.util.Timer
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu!!
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.removeItem(R.id.favorites)
        menu?.findItem(R.id.settings)
            ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
        return super.onPrepareOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorites ->
                Toast.makeText(this, "Favorites clicked", Toast.LENGTH_SHORT)
                    .show()

            R.id.addContact ->
                Toast.makeText(this, "Add contact clicked", Toast.LENGTH_SHORT)
                    .show()
        }
        return true
    }

    // Does not work
//    override fun onOptionsMenuClosed(menu: Menu?) {
//        Toast.makeText(this, "Closed", Toast.LENGTH_SHORT).show()
//        super.onOptionsMenuClosed(menu)
//    }

    override fun closeOptionsMenu() {
        super.closeOptionsMenu()
        Toast.makeText(this, "Closed", Toast.LENGTH_SHORT).show()
    }

    override fun openOptionsMenu() {
        super.openOptionsMenu()
        Toast.makeText(this, "Opened", Toast.LENGTH_SHORT).show()
    }

    override fun invalidateOptionsMenu() {
        super.invalidateOptionsMenu()
        Toast.makeText(this, "Invalidated", Toast.LENGTH_SHORT).show()
    }




}
