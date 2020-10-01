package com.eburg_soft.televisionbroadcasting.data.datasource.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = GroupEntity.TABLE_NAME)
class DayEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID, index = true)
    val id: String = "",
    @ColumnInfo(name = COLUMN_NAME)
    val date: String = ""
) : Parcelable {

    companion object {

        const val TABLE_NAME = "days"
        const val COLUMN_ID = "day_id"
        const val COLUMN_NAME = "day_date"
    }
}