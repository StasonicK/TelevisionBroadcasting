package com.eburg_soft.televisionbroadcasting.data.datasource.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface DayDao {

    @Query("SELECT * from ${DayEntity.TABLE_NAME}")
    fun getAllDays(): Flowable<List<DayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDays(groups: List<DayEntity>): Completable

    @Query("DELETE FROM ${DayEntity.TABLE_NAME}")
    fun deleteAllDays(): Completable
}