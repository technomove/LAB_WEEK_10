package com.example.lab_week_10.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "total_table")
data class TotalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val total: Int
)
