package com.example.androidlearning

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val firstFragment = FirstFragment()
    private val secondFragment = SecondFragment()
    private val thirdFragment = ThirdFragment()
    private val fourthFragment = FourthFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.button.setOnClickListener {
//            it.layoutParams.height += 20 // height is -2 initally, why?
//            it.layoutParams.width += 20 // width is -2 initally, why?
//            it.requestLayout()
//        }

        supportFragmentManager.addFragmentOnAttachListener { fragmentManager, fragment ->
            Log.d(
                "onAttachListener",
                "name: ${fragment.javaClass.name}, stackSize: ${fragmentManager.backStackEntryCount}"
            )
        }

        supportFragmentManager.addOnBackStackChangedListener {
            Log.d("onAttachListener", "backstack changed: ${supportFragmentManager.fragments}")
        }


        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(binding.fragmentContainer.id, firstFragment)
            .commit()

        supportFragmentManager.commit {

        }

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.first -> replaceFragment(firstFragment)
                R.id.second -> replaceFragment(secondFragment)
                R.id.third -> replaceFragment(thirdFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    private fun replaceWithFirst() {
        supportFragmentManager.commit {
            replace<FirstFragment>(binding.fragmentContainer.id)
            setReorderingAllowed(true)
            addToBackStack("first")
        }
    }

    private fun replaceWithSecond() {
        supportFragmentManager.commit {
            replace<SecondFragment>(binding.fragmentContainer.id)
            setReorderingAllowed(true)
            addToBackStack("first")
        }
    }

}
