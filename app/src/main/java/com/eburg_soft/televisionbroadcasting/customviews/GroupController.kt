package com.eburg_soft.televisionbroadcasting.customviews

import com.airbnb.epoxy.TypedEpoxyController
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity

class GroupController (
    private val callback: Callback
): TypedEpoxyController<List<GroupEntity>>() {

    interface Callback {
        fun onImageClick(album: GroupEntity, position: Int)
    }

    override fun buildModels(data: List<GroupEntity>?) {
        data?.forEachIndexed { index, album ->
//            imageHolder {
//                id(album.albumName)
//                album(album)
//                listener { callback.onImageClick(album, index) }
            }
        }
    }
}