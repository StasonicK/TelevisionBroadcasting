package com.eburg_soft.televisionbroadcasting.data.datasource.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface GroupDao {

    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME}")
    fun getAllGroups(): Flowable<List<GroupEntity>>

    @Query("SELECT ${GroupEntity.COLUMN_ID} FROM ${GroupEntity.TABLE_NAME} where ${GroupEntity.COLUMN_ID} = :groupId")
    fun getSelectedGroupById(groupId: String): Flowable<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(groups: List<GroupEntity>): Completable

    @Query("DELETE FROM ${GroupEntity.TABLE_NAME}")
    fun deleteAllGroups(): Completable
}