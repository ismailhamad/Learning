package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
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
import kotlinx.android.synthetic.main.item_exploer.view.*
import kotlinx.android.synthetic.main.item_favorite.view.*
import java.util.*

class favoriteAD : RecyclerView.Adapter<favoriteAD.ViewHolder>() {
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = differ.currentList[position]

        holder.itemView.apply {

            val gd = GradientDrawable(
                GradientDrawable.Orientation.BR_TL, intArrayOf(-0x9e9d9f, -0xececed)
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
            val intArray = intArrayOf(color, color2,)


            name_fav.text=course.namecourse
            item_card_fav.background =gd
            gd.cornerRadius =80f
            Glide.with(this).load(course.image).into(imageView_fav)
//            imageView10.transitionName=course.image.toString()
//            name_myCourse.transitionName=course.name.toString()
            setOnClickListener {
                onItemClickListener?.let { it(course,imageView_fav,name_fav,intArray) }
            }


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