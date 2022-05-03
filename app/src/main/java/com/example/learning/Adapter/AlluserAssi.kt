package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Model.users
import com.example.learning.R
import kotlinx.android.synthetic.main.item_alluser_ass.view.*
import kotlinx.android.synthetic.main.item_show_student.view.*
import java.util.*
import kotlin.collections.HashMap

class AlluserAssi : RecyclerView.Adapter<AlluserAssi.ViewHolder>() {
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    private val differCallback = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_alluser_ass, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = differ.currentList[position]
        val userAssi = user as HashMap<String, Any?>

        holder.itemView.apply {

            val random = Random()
            val color =
                Color.argb(100, random.nextInt(255), random.nextInt(40), random.nextInt(200))


            name_assi_User.text=userAssi.get("name").toString()
          //  Email_Student.text = user.email



//            setOnClickListener {
//                onItemClickListener?.let { it(user) }
//            }
            pdf_userAssi.setOnClickListener {
                onItemClickListener?.let { userAssi.get("file")?.let { it1 -> it(it1) } }
            }


        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Any) -> Unit)? = null
    fun setOnItemClickListener(listener: (Any) -> Unit) {
        onItemClickListener = listener
    }
}