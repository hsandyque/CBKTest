package com.test.cbktest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.cbktest.R
import com.test.cbktest.listener.PlantListClickListener
import com.test.cbktest.model.Plant
import kotlinx.android.synthetic.main.item_plant_list.view.*
import kotlinx.android.synthetic.main.item_plant_list_empty.view.*

class PlantListAdapter(private val listener: PlantListClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var datas: ArrayList<Plant>? = null
    private val ITEM_NORMAL = 0
    private val ITEM_EMPTY = 1

    fun setData(list: ArrayList<Plant>) {
        datas = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType === ITEM_NORMAL) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_plant_list,
                parent,
                false
            )
            ViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_plant_list_empty,
                parent,
                false
            )
            EmptyViewHolder(view)
        }
    }
    override fun getItemViewType(position: Int): Int {
        datas?.let {
            return if(it.isEmpty()) {
                ITEM_EMPTY
            } else {
                ITEM_NORMAL
            }
        }

        return ITEM_NORMAL
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            datas?.let {
                val currentItem = it[position]
                holder.bindCover(currentItem.pic01_url)
                holder.bindName(currentItem.name_ch)
                holder.bindInfo(currentItem.also_known)
                holder.setClickListener(listener, currentItem)
            }
        } else if(holder is EmptyViewHolder){
            holder.showEmptyView()
        }
    }

    override fun getItemCount(): Int {
        datas?.let {
            if(it.isEmpty()) {
                return 1
            }
            return it.size
        }

        return 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindCover(url: String) {
            itemView.iv_plant_list_cover.apply {
                transitionName = url
                Glide.with(this)
                    .load(url)
                    .centerCrop()
                    .into(this)
            }
        }
        fun bindName(name: String) {
            itemView.tv_plant_name.text = name
        }

        fun bindInfo(info: String) {
            itemView.tv_plant_info.text = info
        }

        fun setClickListener(listener: PlantListClickListener, plant: Plant) {
            itemView.setOnClickListener {
                listener.onClick(plant, itemView.iv_plant_list_cover)
            }
        }
    }

    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showEmptyView() {
            itemView.tv_empty.visibility = View.VISIBLE
        }
    }
}