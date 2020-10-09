package com.eburg_soft.televisionbroadcasting.data.datasource.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity.Companion.COLUMN_GROUP_ID
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = GroupEntity::class,
        parentColumns = [GroupEntity.COLUMN_ID],
        childColumns = [COLUMN_GROUP_ID],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class ChannelEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID, index = true)
    val id: String = "",
    @ColumnInfo(name = COLUMN_GROUP_ID, index = true)
    val groupId: String = "",
    @ColumnInfo(name = COLUMN_NAME)
    val name: String = "",
    @ColumnInfo(name = COLUMN_LOGO_URL)
    val logoUrl: String = "",
    @ColumnInfo(name = COLUMN_IS_SELECTED)
    val isSelected: Boolean = false
) : Parcelable {

    companion object {

        const val TABLE_NAME = "channels"
        const val COLUMN_ID = "channel_id"
        const val COLUMN_GROUP_ID = "group_id"
        const val COLUMN_NAME = "channel_name"
        const val COLUMN_LOGO_URL = "channel_logo_url"
        const val COLUMN_IS_SELECTED = "channel_is_selected"
    }
}