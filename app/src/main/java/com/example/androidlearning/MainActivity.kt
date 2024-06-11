package com.example.androidlearning

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val animDuration = 50L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* ViewPropertyAnimator */
        binding.button.setOnClickListener {button ->
//            setViewPropertyAnimatorListener()
//            viewPropertyAnimator1()
//            viewPropertyAnimator2()

//            val animation = AnimationUtils.makeInAnimation(this, true)
//            button.startAnimation(animation)

            ValueAnimator.ofInt(0, 100).let {
                it.setDuration(2000L)
                it.addUpdateListener {animator ->
                    val value = animator.animatedValue as Int

                }
            }





        }
    }

    private fun setViewPropertyAnimatorListener(){
        val viewPropertyAnimator = binding.button.animate()
        viewPropertyAnimator.setListener(object: AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationCancel(animation: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(animation: Animator?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun viewPropertyAnimator1() {
        val viewPropertyAnimator = binding.button.animate()
        viewPropertyAnimator
            .move(20f, animDuration)
            .withEndAction {
                viewPropertyAnimator
                    .move(-40f, animDuration)
                    .withEndAction {
                        viewPropertyAnimator
                            .move(30f, animDuration)
                            .withEndAction {
                                viewPropertyAnimator
                                    .move(-20f, animDuration)
                            }
                    }
            }
    }

    private fun viewPropertyAnimator2() {
        val viewPropertyAnimator = binding.button.animate()
        viewPropertyAnimator
            .setDuration(animDuration)
            .translationXBy(20f)
            .withEndAction {
                viewPropertyAnimator
                    .setDuration(animDuration)
                    .translationXBy(-40f)
                    .withEndAction {
                        viewPropertyAnimator
                            .setDuration(animDuration)
                            .translationXBy(30f)
                            .withEndAction {
                                viewPropertyAnimator
                                    .setDuration(animDuration)
                                    .translationXBy(-20f)
                                    .start()
                            }
                            .start()
                    }.start()
            }
    }

    private fun ViewPropertyAnimator.move(by: Float, duration: Long): ViewPropertyAnimator {
        println("moved by $by")
        setDuration(duration)
            .translationXBy(by)
            .start()
        return this
    }


}
