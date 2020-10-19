package com.eburg_soft.televisionbroadcasting.presentation.main.adapters

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eburg_soft.televisionbroadcasting.R
import com.eburg_soft.televisionbroadcasting.core.Constants
import com.eburg_soft.televisionbroadcasting.customviews.SnappyAdapter
import com.eburg_soft.televisionbroadcasting.data.datasource.database.models.ChannelEntity
import com.eburg_soft.televisionbroadcasting.extensions.inflate
import com.eburg_soft.televisionbroadcasting.presentation.main.adapters.ChannelsSnappyAdapter.ChannelViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_channel.view.img_channel_icon
import kotlinx.android.synthetic.main.item_channel.view.tv_channel_name

class ChannelsSnappyAdapter : SnappyAdapter<ChannelViewHolder>() {

    inner class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var onClick: OnClick? = null
        var onTouch: OnTouch? = null
        var item: ChannelEntity? = null
        private var rect: Rect? = null
        private var isTouching: Boolean = false

        init {
            view.setOnClickListener {
                val previousItemPosition = selectedItemPosition
                selectedItemPosition = adapterPosition
                val selectedItem = if (selectedItemPosition != -1) getItemAt(selectedItemPosition) else null
                val previousItem = if (previousItemPosition != -1) getItemAt(previousItemPosition) else null
                onClick?.onClick(
                    previousItem,
                    previousItemPosition,
                    selectedItem,
                    selectedItemPosition
                )

                if (getSnapper()?.getSnappedPosition() !== adapterPosition) {
                    getSnapper()?.smoothSnapToPosition(adapterPosition)
                }
            }
            view.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_UP) {
                    // Construct a rect of the view's bounds
                    rect = Rect(v.left, v.top, v.right, v.bottom)

                    if (!rect?.contains(v.left + event.x.toInt(), v.top + event.y.toInt())!!) {
                        // User moved outside bounds
                        isTouching = false
                        onTouch?.onTouch(isTouching)
                    } else {
                        isTouching = true
                        onTouch?.onTouch(isTouching)
                    }
                }
                false
            }
        }

        fun changeSelectedView(isSelected: Boolean) {
//            if (isSelected) {
//                itemView.apply {
//                    tv_channel_name.visibility = View.VISIBLE
//                    linear_channel_img_background.setBackgroundColor(resources.getColor(R.color.blue_light))
//                }
//            } else {
//                itemView.apply {
//                    tv_channel_name.visibility = View.INVISIBLE
//                    linear_channel_img_background.setBackgroundColor(resources.getColor(R.color.black))
//                }
//            }
        }

        fun bind(item: ChannelEntity) {
            this.item = item
            this.itemView.apply {
                tv_channel_name.text = item.name

                Picasso.get()
                    .load(Constants.BASE_URL + item.logoUrl)
                    .placeholder(R.drawable.old_tv_white_32)
                    .error(R.drawable.old_tv_white_32)
                    .fit()
                    .into(img_channel_icon)
            }
        }
    }

    private val dataList: ArrayList<ChannelEntity> = arrayListOf()

    fun setData(list: List<ChannelEntity>?) {
        this.dataList.clear()
        list?.let { this.dataList.addAll(it) }
        this.notifyDataSetChanged()
    }

    var selectedItemPosition = -1
    var itemPosition = -1

    private var onClick: OnClick? = null

    private var onTouch: OnTouch? = null

    interface OnClick {

        fun onClick(item0: Any?, positionItem0: Int, item1: Any?, positionItem1: Int)
    }

    interface OnTouch {

        fun onTouch(isTouching: Boolean)
    }

    override fun getItemCount(): Int = dataList.size

    fun getItemAt(position: Int) = dataList[position]

    override fun onBindViewHolder(vh: ChannelViewHolder, position: Int, isAtTheCenter: Boolean) {
        vh.apply {
            bind(dataList[position])
            onClick = onClick
            onTouch = onTouch
        }
        if (isAtTheCenter) {
            itemPosition = selectedItemPosition
            selectedItemPosition = position
            vh.changeSelectedView(true)
        } else {
            vh.changeSelectedView(false)
        }
    }

    override fun onSnapedFromCenter(vh: ChannelViewHolder) {
        vh.changeSelectedView(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = parent.inflate(R.layout.item_channel)
        return ChannelViewHolder(view)
    }
}