package com.example.lab_week_10.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {

    val total = MutableLiveData<Int>()

    fun addNumber(number: Int) {
        val currentTotal = total.value ?: 0
        total.value = currentTotal + number
    }
}
