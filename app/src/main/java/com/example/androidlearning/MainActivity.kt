package com.example.androidlearning

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.MutableCreationExtras
import com.example.androidlearning.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //* Create ViewModel via Factory (Factory injects dependencies)
    private val viewModel1 = MainViewModelFactory().create(MainViewModel::class.java)

    //* Create ViewModel via Factory with DI (Constructor injection)
    private val viewModel2 = MainViewModelFactory2(MyApp.appModule.authRepository)
        .create(MainViewModel::class.java)

    //* Create ViewModel via Factory with CreationExtras
    private val viewModel3 = MainViewModelFactory3().create(
        MainViewModel::class.java,
        //? Injection via CreationExtras
        MutableCreationExtras().apply {
            set(MainViewModel.AUTH_REPO_DI_KEY, MyApp.appModule.authRepository)
        }
    )

    //* Create ViewModel via Factory with helper function
    private val viewModel = viewModelFactory {
        //? Constructor injection
        MainViewModel(MyApp.appModule.authRepository)
    }.create(MainViewModel::class.java)

    //* Create ViewModel via viewModels with Factory and extras
    private val viewModel4: MainViewModel by viewModels(
        //? You can skip extrasProducer if you don't have any extras
        extrasProducer = {
            //? Injection via CreationExtras
            MutableCreationExtras().apply {
                set(MainViewModel.AUTH_REPO_DI_KEY, MyApp.appModule.authRepository)
            }
        },
        //? You can skip factoryProducer if you don't have any custom factory
        factoryProducer = {
            //? If you skip extrasProducer, you can do the injection here
            MainViewModelFactory3()
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel1.login()
        viewModel2.login()
        viewModel3.login()
        viewModel4.login()
        viewModel.login()
    }


}
