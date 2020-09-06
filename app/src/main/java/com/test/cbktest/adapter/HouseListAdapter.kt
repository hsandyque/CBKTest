package com.test.cbktest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.cbktest.R
import com.test.cbktest.listener.HouseListClickListener
import com.test.cbktest.model.House
import kotlinx.android.synthetic.main.item_house_list.view.*

class HouseListAdapter(private val listener: HouseListClickListener):
    RecyclerView.Adapter<HouseListAdapter.ViewHolder>() {
    private var datas: ArrayList<House> = arrayListOf()

    fun setData(list: ArrayList<House>) {
        datas = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_house_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = datas[position]
        holder.bindCover(currentItem.pic_url)
        holder.bindName(currentItem.name)
        holder.bindInfo(currentItem.info)
        holder.bindMemo(currentItem.memo)
        holder.setClickListener(listener, currentItem)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindCover(url: String) {
            Glide.with(itemView.iv_house_list_cover)
                .load(url).centerCrop().into(itemView.iv_house_list_cover)
        }
        fun bindName(name: String) {
            itemView.tv_house_name.text = name
        }

        fun bindInfo(info: String) {
            itemView.tv_house_info.text = info
        }

        fun bindMemo(memo: String) {
            itemView.tv_house_memo.text = memo
        }

        fun setClickListener(listener: HouseListClickListener, house: House) {
            itemView.setOnClickListener {
                listener.onClick(house)
            }
        }
    }
}