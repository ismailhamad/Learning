package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Model.Assignment
import com.example.learning.Model.lecture
import com.example.learning.R
import kotlinx.android.synthetic.main.item_assigment.view.*
import kotlinx.android.synthetic.main.item_lecture_tech.view.*

class assigmentAD: RecyclerView.Adapter<assigmentAD.ViewHolder>() {
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)


    private val differCallback = object : DiffUtil.ItemCallback<Assignment>() {
        override fun areItemsTheSame(oldItem: Assignment, newItem: Assignment): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Assignment, newItem: Assignment): Boolean {
            return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_assigment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val assigment = differ.currentList[position]

        holder.itemView.apply {
            name_assigment.text = assigment.name
            setOnClickListener {
                onItemClickListener?.let { it(assigment) }
            }

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Assignment) -> Unit)? = null
    fun setOnItemClickListener(listener: (Assignment) -> Unit) {
        onItemClickListener = listener
    }


}