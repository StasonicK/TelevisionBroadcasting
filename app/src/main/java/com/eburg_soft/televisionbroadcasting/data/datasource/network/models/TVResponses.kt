package com.eburg_soft.televisionbroadcasting.data.datasource.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroupResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("channels")
    var channelResponses: List<ChannelResponse>
) : Parcelable {}

@Parcelize
data class ChannelResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("logoUrl")
    var logoUrl: String
) : Parcelable {}


@Parcelize
data class ProgramResponse(
    val id: String,
    val name: String
) : Parcelable {}