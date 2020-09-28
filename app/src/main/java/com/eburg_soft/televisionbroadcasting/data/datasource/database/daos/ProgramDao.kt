package com.eburg_soft.televisionbroadcasting.data.datasource.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity
import com.eburg_soft.televisionbroadcasting.data.datasource.network.models.ProgramResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ProgramDao {

    @Query("SELECT * FROM ${ProgramEntity.TABLE_NAME} WHERE ${ProgramEntity.COLUMN_ID} like :programId")
    fun getProgramsById(programId: String): Flowable<List<ProgramEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrograms(programs: List<ProgramEntity>): Completable

    @Query("DELETE FROM ${ProgramEntity.TABLE_NAME}")
    fun deleteAllPrograms(): Completable
}