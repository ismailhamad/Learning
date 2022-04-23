package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learning.Model.course
import com.example.learning.Model.users
import com.example.learning.R
import kotlinx.android.synthetic.main.item_courses.view.*
import kotlinx.android.synthetic.main.item_show_student.view.*
import java.util.*

class showStudentAD : RecyclerView.Adapter<showStudentAD.ViewHolder>() {
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<users>() {
        override fun areItemsTheSame(oldItem: users, newItem: users): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: users, newItem: users): Boolean {
            return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_show_student, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = differ.currentList[position]

        holder.itemView.apply {

            val random = Random()
            val color =
                Color.argb(100, random.nextInt(255), random.nextInt(40), random.nextInt(200))


                nameStudent.text=user.name
                Email_Student.text = user.email



            setOnClickListener {
                onItemClickListener?.let { it(user) }
            }


        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((users) -> Unit)? = null
    fun setOnItemClickListener(listener: (users) -> Unit) {
        onItemClickListener = listener
    }
}