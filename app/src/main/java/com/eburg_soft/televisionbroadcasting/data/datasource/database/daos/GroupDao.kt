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

    @Query("SELECT * from `groups`")
    fun getAllGroups(): Flowable<List<GroupEntity>>

    @Query("SELECT * from `groups` LIMIT 1")
    fun getFirstGroup(): Flowable<List<GroupEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(groups: List<GroupEntity>): Completable

    @Query("DELETE FROM `groups`")
    fun deleteAllGroups(): Completable
}