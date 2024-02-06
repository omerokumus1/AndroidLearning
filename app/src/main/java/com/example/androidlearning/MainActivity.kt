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

        //? Do not call when using toolbar functions
//        setSupportActionBar(binding.toolbar)

        //? onCreateOptionsMenu
        binding.toolbar.inflateMenu(R.menu.toolbar_menu)

        //? onOptionsItemSelected
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorites ->
                    Toast.makeText(this, "Favorites clicked", Toast.LENGTH_SHORT)
                        .show()

                R.id.addContact ->
                    Toast.makeText(this, "Add contact clicked", Toast.LENGTH_SHORT)
                        .show()
            }
            true
        }


        //? onPrepareOptionsMenu
        //? No equivalent for toolbar


        /*? Navigation Icon
        ? You can set this via XML by app:navigationIcon="@drawable/ic_arrow_back"
        ? Using navigation icon instead of a simple button is a good practice
        ? because it's purpose is clear
        ? and it has the responsibility of navigating back to the previous screen.
        ? You can also set a click listener to it.
        ? It also has a default animation when clicked.
         */
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)

        binding.toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Back clicked", Toast.LENGTH_SHORT).show()
        }

        //? hide menu
        Timer().schedule(timerTask {
            runOnUiThread {
                binding.toolbar.hideOverflowMenu()
            }
        }, 3000)

        //? show menu
        Timer().schedule(timerTask {
            runOnUiThread {
                binding.toolbar.showOverflowMenu()
            }
        }, 6000)

        //? overflow icon
        binding.toolbar.overflowIcon =
            getDrawable(R.drawable.ic_menu_24)


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

    //! Does not work
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


    override fun supportInvalidateOptionsMenu() {
        super.supportInvalidateOptionsMenu()
    }




}
