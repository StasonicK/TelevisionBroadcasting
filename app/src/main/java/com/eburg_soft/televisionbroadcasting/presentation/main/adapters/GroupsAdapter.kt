package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.eburg_soft.televisionbroadcasting.core.BaseAdapter
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.GroupEntity

class GroupAdapter :BaseAdapter<GroupEntity,GroupAdapter>(GroupDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return
    }

    class GroupViewHolder(val view:View):BaseViewHolder(view){

        override fun onBind(item: Any) {
            TODO("Not yet implemented")
        }
    }
}

