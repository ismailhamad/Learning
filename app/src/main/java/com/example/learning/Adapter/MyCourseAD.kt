package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Model.course
import com.example.learning.Model.myCourse
import com.example.learning.R
import com.example.learning.View.MyCourseFragment.Companion.xx
import kotlinx.android.synthetic.main.item_mycourse.view.*


class MyCourseAD: RecyclerView.Adapter<MyCourseAD.ViewHolder>() {
     var rowindex:Int? = null
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<myCourse>() {
        override fun areItemsTheSame(oldItem: myCourse, newItem: myCourse): Boolean {
            return oldItem.id == newItem.id
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
            setOnClickListener {
                onItemClickListener?.let { it(course) }
                rowindex = position
                notifyDataSetChanged();
            }
            if (rowindex==position){
                item_card.radius=20f
                item_card.setCardBackgroundColor(Color.parseColor("#9B67F6"))
                item_card.cardElevation=20f
            }else{
                item_card.cardElevation=0f
                item_card.radius=20f
                item_card.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"))
                item_card.elevation=0f
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