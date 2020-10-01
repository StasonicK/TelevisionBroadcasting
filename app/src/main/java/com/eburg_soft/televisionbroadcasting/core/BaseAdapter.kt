package com.eburg_soft.televisionbroadcasting.core

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Item : Any, VH : BaseAdapter.BaseViewHolder>(
    diff: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, VH>(diff) {

    var onClick: OnClick? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))

        holder.onClick = onClick
    }

    fun setOnClick(click: (Any?, View) -> Unit) {
        onClick = object : OnClick {
            override fun onClick(item: Any?, view: View) {
                click(item, view)
            }
        }
    }

    interface OnClick {

        fun onClick(item: Any?, view: View)
    }

    abstract class BaseViewHolder(protected val view: View) : RecyclerView.ViewHolder(view) {

        var onClick: OnClick? = null
        var item: Any? = null

        init {
            view.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        protected abstract fun onBind(item: Any)

        fun bind(item: Any) {
            this.item = item

            onBind(item)
        }
    }
}

