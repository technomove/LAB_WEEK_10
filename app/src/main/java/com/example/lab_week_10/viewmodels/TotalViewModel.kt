package com.example.lab_week_10.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_week_10.data.TotalDao
import com.example.lab_week_10.data.TotalEntity
import com.example.lab_week_10.data.TotalObject
import kotlinx.coroutines.launch
import java.util.Date

class TotalViewModel : ViewModel() {

    val total = MutableLiveData<Int>()
    lateinit var totalDao: TotalDao

    fun setDao(dao: TotalDao) {
        this.totalDao = dao
    }

    fun addNumber(number: Int) {
        val currentTotal = total.value ?: 0
        val newTotal = currentTotal + number
        total.value = newTotal

        val totalObject = TotalObject(
            value = newTotal,
            date = Date().toString()
        )

        viewModelScope.launch {
            totalDao.insertTotal(
                TotalEntity(total = totalObject)
            )
        }
    }
}
