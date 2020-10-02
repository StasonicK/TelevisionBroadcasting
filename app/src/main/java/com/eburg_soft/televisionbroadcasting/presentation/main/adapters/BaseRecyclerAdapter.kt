package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.BaseRecyclerAdapter.BaseViewHolder

abstract class BaseRecyclerAdapter<T, V> : RecyclerView.Adapter<BaseViewHolder>() {

    var list: ArrayList<V> = arrayListOf()

    var onClick: OnClick? = null

    interface OnClick {

        fun onClick(item: Any?, view: View)
    }

    fun setOnClick(click: (Any?, View?) -> Unit) {
        onClick = object : OnClick {
            override fun onClick(item: Any?, view: View) {
                click(item, view)
            }
        }
    }

    fun getItemAt(position: Int): V = list[position]

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItemId(position))

        holder.onClick = onClick
    }

    override fun getItemCount(): Int = list.size

//    abstract fun updateAdapter(updatedList: List<V>)

    fun setData(list: List<V>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder(protected val view: View) : RecyclerView.ViewHolder(view) {

        var onClick: OnClick? = null
        var item: Any? = null

        init {
            view.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        protected abstract fun onBind(item: Any?)

        fun bind(item: Any?) {
            this.item = item
            onBind(item)
        }
    }
}