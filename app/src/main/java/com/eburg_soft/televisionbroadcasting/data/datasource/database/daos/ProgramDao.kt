package com.eburg_soft.televisionbroadcasting.data.datasource.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ProgramDao {

    @Query("SELECT * FROM ${ProgramEntity.TABLE_NAME}")
    fun getAllPrograms(): Flowable<List<ProgramEntity>>

    @Query("SELECT * FROM ${ProgramEntity.TABLE_NAME} WHERE ${ProgramEntity.COLUMN_ID} like :id")
    fun getProgramById(id: String): Flowable<List<ProgramEntity>>

    @Query("SELECT * FROM ${ProgramEntity.TABLE_NAME} WHERE ${ProgramEntity.COLUMN_CHANNEL_ID} like :programId")
    fun getProgramsByChannelId(programId: String): Flowable<List<ProgramEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrograms(programs: List<ProgramEntity>): Completable

    @Query("DELETE FROM ${ProgramEntity.TABLE_NAME}")
    fun deleteAllPrograms(): Completable
}