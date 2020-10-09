package com.eburg_soft.televisionbroadcasting.data.datasource.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.DayEntity.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class DayEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID, index = true)
    val id: String = "",
    @ColumnInfo(name = COLUMN_NAME)
    val date: String = "",
    @ColumnInfo(name = COLUMN_IS_SELECTED)
    val isSelected: Boolean = false
) : Parcelable {

    companion object {

        const val TABLE_NAME = "days"
        const val COLUMN_ID = "day_id"
        const val COLUMN_NAME = "day_date"
        const val COLUMN_IS_SELECTED = "day_is_selected"
    }
}