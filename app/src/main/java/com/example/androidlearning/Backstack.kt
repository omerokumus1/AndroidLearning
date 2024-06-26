package com.example.androidlearning

object Backstack {
    private val backstack = mutableListOf<String>()

    fun print() {
        println("Current backstack: \n\t $backstack")
    }

    fun push(activityName: String) {
        backstack.add(activityName)
        println("$activityName pushed onto the backstack")
        this.print()
    }

    fun pop() {
        val last = backstack.last()
        backstack.removeLast()
        println("$last popped from the backstack")
        this.print()
    }

}