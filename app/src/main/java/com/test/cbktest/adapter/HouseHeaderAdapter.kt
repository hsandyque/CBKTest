package com.test.cbktest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.cbktest.R
import com.test.cbktest.listener.HouseHeaderClickListener
import com.test.cbktest.model.House
import kotlinx.android.synthetic.main.item_house_header.view.*

class HouseHeaderAdapter(private val context: Context,
                         private val listener: HouseHeaderClickListener):
    RecyclerView.Adapter<HouseHeaderAdapter.ViewHolder>() {
    private lateinit var data: House

    fun setData(data: House) {
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_house_header, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data
        holder.bindCover(context, currentItem.pic_url)
        holder.bindInfo(currentItem.info)
        holder.bindMemo(currentItem.memo)
        holder.bindCategory(currentItem.category)
        holder.setClickListener(listener, currentItem.url)
    }

    override fun getItemCount(): Int {
        return 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindCover(context: Context, url: String) {
            Glide.with(itemView.iv_house_header_cover)
                .load(url).into(itemView.iv_house_header_cover)
        }
        fun bindInfo(info: String) {
            itemView.tv_house_detail_info.text = info
        }

        fun bindMemo(memo: String) {
            itemView.tv_house_detail_memo.text = memo
        }

        fun bindCategory(category: String) {
            itemView.tv_house_detail_category.text = category
        }

        fun setClickListener(listener: HouseHeaderClickListener, url: String) {
            itemView.tv_house_detail_hyper_link.setOnClickListener {
                listener.onClick(url)
            }
        }
    }
}