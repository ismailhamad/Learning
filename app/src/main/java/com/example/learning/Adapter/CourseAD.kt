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
import com.example.learning.Model.course
import com.example.learning.R
import kotlinx.android.synthetic.main.item_courses.view.*
import java.util.*


class CourseAD: RecyclerView.Adapter<CourseAD.ViewHolder>() {
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<course>() {
        override fun areItemsTheSame(oldItem: course, newItem: course): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: course, newItem: course): Boolean {
            return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_courses, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = differ.currentList[position]

        holder.itemView.apply {
//            val remainder: Int = getAdapterPosition() % colorsArray.size()
//            mView.setCardBackgroundColor(Color.parseColor(colorsArray.get(remainder)))
            val random = Random()
            val color =
                Color.argb(100, random.nextInt(255), random.nextInt(40), random.nextInt(200))
//            val r = Random()
//            val red: Int = r.nextInt(100 - 0 + 1) + 0
//            val green: Int = r.nextInt(40 - 0 + 1) + 0
//            val blue: Int = r.nextInt(200 - 0 + 1) + 0
//
//            val draw = GradientDrawable()
//            draw.shape = GradientDrawable.RECTANGLE
//            draw.setColor(Color.rgb(red, green, blue))
            item_c.setCardBackgroundColor(color)
            nameCourse.text=course.name
            Glide.with(this).load(course.image).into(imageCourse)
            setOnClickListener {
                onItemClickListener?.let { it(course) }
            }


        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((course) -> Unit)? = null
    fun setOnItemClickListener(listener: (course) -> Unit) {
        onItemClickListener = listener
    }
}