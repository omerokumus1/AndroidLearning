package com.example.androidlearning

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.let {
//            it.title = "  Action Bar"
//            it.subtitle = "  Default action bar"
//
//            // Does not work until setDisplayShowHomeEnabled(true) is called
////            it.setIcon(R.drawable.ic_favorite)
////            it.setDisplayShowHomeEnabled(true)
//
//            // Does not work until setDisplayShowHomeEnabled(true) and
//            // setDisplayUseLogoEnabled(true) are called
//            it.setLogo(R.drawable.logo)
//            it.setDisplayShowHomeEnabled(true)
//            it.setDisplayUseLogoEnabled(true)


//            it.setDisplayHomeAsUpEnabled(true) // up (back) button

            // it.setHomeAsUpIndicator(R.drawable.back_icon) // -> alternative back button icon

            //it.setBackgroundDrawable(bgDrawable)


        }


//        binding.toolbar.inflateMenu(R.menu.toolbar_menu)
//        binding.toolbar.showOverflowMenu()

        setSupportActionBar(binding.toolbar)


        supportActionBar?.let {
//            it.title = "Toolbar"
            it.subtitle = "Demo"

//            it.setLogo(R.drawable.logo)

//            it.setIcon(R.drawable.ic_add_contact)

//            it.hide()
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorites -> TODO()
            R.id.addContact -> TODO()
        }
        return true
    }


}
