package com.eburg_soft.televisionbroadcasting.data.datasource.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class GroupEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID, index = true)
    val id: String = "",
    @ColumnInfo(name = COLUMN_NAME)
    val name: String = "",
    @ColumnInfo(name = COLUMN_IS_SELECTED)
    val isSelected: Boolean = false
) : Parcelable {

    companion object {

        const val TABLE_NAME = "groups"
        const val COLUMN_ID = "group_id"
        const val COLUMN_NAME = "group_name"
        const val COLUMN_IS_SELECTED = "group_is_selected"
    }
}