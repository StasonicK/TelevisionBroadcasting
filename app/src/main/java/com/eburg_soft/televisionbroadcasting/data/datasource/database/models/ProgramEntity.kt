package com.eburg_soft.televisionbroadcasting.data.datasource.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ProgramEntity.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = ChannelEntity::class,
        parentColumns = [ChannelEntity.COLUMN_ID],
        childColumns = [ProgramEntity.COLUMN_CHANNEL_ID],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class ProgramEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,
    @ColumnInfo(name = COLUMN_CHANNEL_ID)
    val channelId: String,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String
) : Parcelable {

    companion object {

        const val TABLE_NAME = "programs"
        const val COLUMN_ID = "program_id"
        const val COLUMN_CHANNEL_ID = "channel_id"
        const val COLUMN_NAME = "program_name"
    }
}