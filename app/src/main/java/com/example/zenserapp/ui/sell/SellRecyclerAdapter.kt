package com.example.zenserapp.ui.sell

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zenserapp.R

class SellRecyclerAdapter(
        private val exampleList: List<Category>,
        private val listener: OnItemClickListener
        ):RecyclerView.Adapter<SellRecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.category_item,parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
    }

    override fun getItemCount() = exampleList.size

    inner class CustomViewHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {
        val imageView: ImageView = v.findViewById(R.id.categoryIV)
        val textView1: TextView = v.findViewById(R.id.categoryTV)

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}


