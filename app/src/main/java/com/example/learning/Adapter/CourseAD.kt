package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learning.Model.course
import com.example.learning.R
import kotlinx.android.synthetic.main.item_courses.view.*
import java.util.*


class CourseAD: RecyclerView.Adapter<CourseAD.ViewHolder>() {
    var rowindex:Int = 0
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
            val gd = GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(-0x9e9d9f, -0xececed)
            )


            val random = Random()
            val color =
                Color.argb(200, random.nextInt(255), random.nextInt(50), random.nextInt(200))
            val color2 =
                Color.argb(100, random.nextInt(255), random.nextInt(50), random.nextInt(200))
            gd.setColors(
                intArrayOf(
                    color,
                    color2,
                )
            )
            var c =intArrayOf(color,color2)

            setOnTouchListener { v, event ->
                val action = event.action
                when(action){

                    MotionEvent.ACTION_DOWN -> {




                    }


                    MotionEvent.ACTION_MOVE -> {
                        rowindex = position
                    }

                    MotionEvent.ACTION_UP -> {
                        onItemClickListener?.let { it(course,imageCourse,nameCourse,c) }

                    }

                    MotionEvent.ACTION_CANCEL -> {
                        notifyDataSetChanged()
                    }

                    else ->{
                        onItemClickListener?.let { it(course,imageCourse,nameCourse,c) }

                    }

                }
                true

            }

            if (rowindex==position){
                y = -50f

            }else{
                y = +40f
                item_c.elevation = -3f

            }



            item_c.setCardBackgroundColor(color)
            nameCourse.text=course.namecourse
            item_c.background =gd
            gd.cornerRadius =70f
            Glide.with(this).load(course.image).into(imageCourse)
//            imageCourse.transitionName=course.image.toString()
//            nameCourse.transitionName=course.namecourse.toString()



        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((course, ImageView, TextView, IntArray) -> Unit)? = null
    fun setOnItemClickListener(listener: (course, ImageView, TextView, IntArray) -> Unit) {
        onItemClickListener = listener
    }
}