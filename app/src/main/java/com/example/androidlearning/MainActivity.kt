package com.example.androidlearning

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlearning.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        CoroutineScope(Dispatchers.IO).launch {
            val networkCallAnswer = doNetworkCall()
            Log.d(
                "MainActivity",
                "Network Call Answer: $networkCallAnswer"
            )
        }

        // Job
        /*val scope = CoroutineScope(Dispatchers.IO)
        val job = scope.launch {
            val networkCallAnswer = doNetworkCall()
            Log.d(
                "MainActivity",
                "Network Call Answer: $networkCallAnswer"
            )
        }

        job.isActive
        job.isCancelled
        job.isCompleted
        job.cancel()
        job.start()
        job.invokeOnCompletion {
            // called synchronously when job is completed
        }*/

        CoroutineScope(Dispatchers.IO).launch {
            var networkCallAnswer = "ğam"
            val taskDeferred =
                async {
                    delay(2000L)
                    val networkCallAnswer = doNetworkCall()
                    Log.d(
                        "MainActivity",
                        "Network Call Answer: $networkCallAnswer"
                    )
                    networkCallAnswer
                }
            networkCallAnswer = taskDeferred.await()
            Log.d(
                "MainActivity",
                "Network Call Answer after await: $networkCallAnswer"
            )
        }

        // Thread and thread pool creation
        /*val dispatcher = Dispatchers.Default
            .limitedParallelism(3)
        CoroutineScope(dispatcher).launch {

        }

        CoroutineScope(Dispatchers.Main)
            .launch(
                newFixedThreadPoolContext(3, "MyThread")
            ) {

            }*/


        // Cancelling Coroutine Execution



    }

    suspend fun doNetworkCall(): String {
        delay(2000L)
        return "Network Call Answer"
    }

    fun dummy() {

    }


}
