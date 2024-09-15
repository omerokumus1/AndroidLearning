package com.example.androidlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.reflect.KProperty

data class Dice(val value: Int)

class MainActivityViewModel : ViewModel() {

//    private val diceMutableLiveData = MutableLiveData<Dice>()
//    val diceLiveData: LiveData<Dice> = diceMutableLiveData

//    val exposableLiveData by ExposableLiveData<MainActivityViewModel, Dice>()

    val exposableLiveData by ExposableLiveData<Dice>()


    fun rollDice() {
        exposableLiveData.value = Dice((1..6).random())
    }

    class ExposableLiveData<T> {
        private val mutableLiveData = MutableLiveData<T>()

        // This is the getter for the LiveData (publicly exposed)
        operator fun getValue(thisRef: Any?, property: KProperty<*>): LiveData<T> {
            return mutableLiveData
        }

        operator fun getValue(thisRef: ViewModel, property: KProperty<*>): MutableLiveData<T> {
            return mutableLiveData
        }

//        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: LiveData<T>) {
//            mutableLiveData.value = value.value
//        }

        // For setting the value from a background thread
        fun postValue(value: T) {
            mutableLiveData.postValue(value)
        }
    }
}


//class ExposableLiveData<T, E> {
//    private val mutableLiveData = MutableLiveData<E>()
//
//    operator fun getValue(thisRef: T, property: KProperty<*>): LiveData<E> {
//        return mutableLiveData
//    }
//
//    operator fun setValue(thisRef: T, property: KProperty<*>, value: Any?) {
//        mutableLiveData.value = value as E
//    }
//
//}
