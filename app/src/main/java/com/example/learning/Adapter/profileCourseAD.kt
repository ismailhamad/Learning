package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learning.Model.myCourse
import com.example.learning.R
import kotlinx.android.synthetic.main.item_course_profile.view.*
import kotlinx.android.synthetic.main.item_mycourse.view.*
import java.util.*

class profileCourseAD : RecyclerView.Adapter<profileCourseAD.ViewHolder>() {
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_course_profile, parent, false)
        )
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = differ.currentList[position]


        holder.itemView.apply {
            val gd = GradientDrawable(
                GradientDrawable.Orientation.BR_TL, intArrayOf(-0x9e9d9f, -0xececed)
            )
            val gd2 = GradientDrawable(
                GradientDrawable.Orientation.BR_TL, intArrayOf(-0x9e9d9f, -0xececed)
            )

            gd2.setColors(
                intArrayOf(
                    Color.parseColor("#A4B2CEE4"),
                    Color.parseColor("#A4B2CEE4"),
                )
            )
            val random = Random()
            val color =
                Color.argb(200, random.nextInt(255), random.nextInt(50), random.nextInt(200))
            val color2 =
                Color.argb(150, random.nextInt(255), random.nextInt(50), random.nextInt(200))
            gd.setColors(
                intArrayOf(
                    color,
                    color2,
                )
            )

            Glide.with(this).load(course.image).into(imageViewprof)
            name_myCoursepro.text=course.name
            setOnClickListener {
                onItemClickListener?.let { it(course) }
                rowindex = position
                notifyDataSetChanged();
            }





                gd.cornerRadius =80f
                item_cardprof.background =gd
                item_cardprof.cardElevation = 10f



        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((myCourse) -> Unit)? = null
    fun setOnItemClickListener(listener: (myCourse) -> Unit) {
        onItemClickListener = listener
    }
    private var onItemClickListener2: ((myCourse) -> Unit)? = null
    fun setOnItemClickListener2(listener: (myCourse) -> Unit) {
        onItemClickListener2 = listener
    }
}