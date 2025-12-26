package com.example.lab_week_10.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TotalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTotal(totalEntity: TotalEntity)

    @Query("SELECT * FROM total ORDER BY id DESC LIMIT 1")
    suspend fun getLastTotal(): TotalEntity?
}
