package com.example.androidlearning

import android.graphics.Color
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var actionMode: ActionMode? = null
    private var selectedItemCount = 0

    private val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        // Called when the action mode is created. startActionMode() is called.
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items.
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.context_menu, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after
        // onCreateActionMode, and might be called multiple times if the mode
        // is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item.
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.item_share -> {
                    Toast.makeText(this@MainActivity, "Shared", Toast.LENGTH_SHORT).show()
                    mode.finish() // Action picked, so close the CAB.
                    true
                }

                R.id.item_favorites -> {
                    Toast.makeText(this@MainActivity, "Favorited", Toast.LENGTH_SHORT).show()
                    mode.finish() // Action picked, so close the CAB.
                    true
                }

                else -> false
            }
        }

        // Called when the user exits the action mode.
        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imageView1.setOnLongClickListener { view ->
            launchActionMode(view)
        }
        binding.imageView2.setOnLongClickListener { view ->
            launchActionMode(view)
        }
        binding.imageView3.setOnLongClickListener { view ->
            launchActionMode(view)
        }
        binding.imageView4.setOnLongClickListener { view ->
            launchActionMode(view)
        }

        binding.run {
            imageView1.setOnClickListener { view ->
                onClickView(view)
            }
            imageView2.setOnClickListener { view ->
                onClickView(view)
            }
            imageView3.setOnClickListener { view ->
                onClickView(view)
            }
            imageView4.setOnClickListener { view ->
                onClickView(view)
            }
        }

    }

    private fun launchActionMode(view: View) =
        when (actionMode) {
            null -> {
                // Start the CAB using the ActionMode.Callback defined earlier.
                actionMode = this.startActionMode(actionModeCallback)
                view.isSelected = true
                view.setBackgroundColor(Color.MAGENTA)
                selectedItemCount++
                actionMode?.title = "$selectedItemCount selected"
                true
            }

            else -> false
        }

    private fun onClickView(view: View) {
        if (actionMode != null) {
            view.isSelected = !view.isSelected
            if (view.isSelected) {
                view.setBackgroundColor(Color.MAGENTA)
                selectedItemCount++
            } else {
                view.setBackgroundColor(Color.TRANSPARENT)
                selectedItemCount--
            }
            actionMode?.title = "$selectedItemCount selected"
        }
    }

}

