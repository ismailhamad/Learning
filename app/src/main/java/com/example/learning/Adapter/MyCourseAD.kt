package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Model.course
import com.example.learning.Model.myCourse
import com.example.learning.R
import kotlinx.android.synthetic.main.item_mycourse.view.*


class MyCourseAD: RecyclerView.Adapter<MyCourseAD.ViewHolder>() {
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<myCourse>() {
        override fun areItemsTheSame(oldItem: myCourse, newItem: myCourse): Boolean {
            return oldItem.idcourse == newItem.idcourse
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: myCourse, newItem: myCourse): Boolean {
            return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_mycourse, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.itemView.apply {
            name_myCourse.text=course.name
            Desc_MyCourse.text=course.description
            setOnClickListener {
                onItemClickListener?.let { it(course) }
            }


        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((myCourse) -> Unit)? = null
    fun setOnItemClickListener(listener: (myCourse) -> Unit) {
        onItemClickListener = listener
    }
}