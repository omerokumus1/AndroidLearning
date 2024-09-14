package com.example.androidlearning

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChipViewModel : ViewModel() {

    // LiveData to keep track of selected chips (but for both groups!)
    val selectedChipLiveData = MutableLiveData<Int>()

    fun selectChip(chipId: Int) {
        selectedChipLiveData.value = chipId
    }
}
