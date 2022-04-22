package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learning.Model.course
import com.example.learning.R
import kotlinx.android.synthetic.main.item_corse_tech.view.*
import kotlinx.android.synthetic.main.item_courses.view.*
import java.util.*

class CourseTechAD: RecyclerView.Adapter<CourseTechAD.ViewHolder>() {
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_corse_tech, parent, false)
        )
    }

    @SuppressLint("ClickableViewAccessibility")
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
            item_cT.setCardBackgroundColor(color)
            nameCourseT.text=course.name
            Glide.with(this).load(course.image).into(imageCourseT)

            setOnClickListener {
                onItemClickListener?.let { it(course) }

            }
            popmune.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(context,popmune)
                popupMenu.menuInflater.inflate(R.menu.menu_edit,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.updateCourse ->
                            Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        R.id.deleteCourse ->
                            Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()

                    }
                    true
                })
                popupMenu.show()
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

    private var onItemClickListener2: ((course) -> Unit)? = null
    fun setOnItemClickListener2(listener: (course) -> Unit) {
        onItemClickListener2 = listener
        notifyDataSetChanged()
    }
}