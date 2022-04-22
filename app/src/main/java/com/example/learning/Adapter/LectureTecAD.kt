package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Model.lecture
import com.example.learning.R
import kotlinx.android.synthetic.main.item_lecture.view.*
import kotlinx.android.synthetic.main.item_lecture_tech.view.*

class LectureTecAD: RecyclerView.Adapter<LectureTecAD.ViewHolder>() {
    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item)


    private val differCallback = object : DiffUtil.ItemCallback<lecture>() {
        override fun areItemsTheSame(oldItem: lecture, newItem: lecture): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: lecture, newItem: lecture): Boolean {
            return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_lecture_tech, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lecture = differ.currentList[position]

        holder.itemView.apply {
            name_lectTec.text = lecture.name
            if(lecture.seeLecture==false){
                name_lectTec.setTextColor(Color.parseColor("#689A9797"));
                cc_lectTEC.setCardBackgroundColor(Color.parseColor("#689A9797"))
                check_seeeTech.setCardBackgroundColor(Color.parseColor("#689A9797"))
            }else{

                cc_lectTEC.setCardBackgroundColor(Color.WHITE)
            }


            but_hidden.setOnClickListener {
                onItemClickListener2?.let { it(lecture) }
            }


//            tv_desLecture.text = lecture.description
//            if (lecture.video != null){
//                val mediaController = MediaController(context)
//                mediaController.setAnchorView(video_getLecture)
//                val uri = Uri.parse("${lecture.video}")
//                video_getLecture.setMediaController(mediaController)
//                video_getLecture.setVideoURI(uri)
//                video_getLecture.requestFocus()
//            }else{
//                video_getLecture.visibility = View.INVISIBLE
//            }
            setOnClickListener {
                onItemClickListener?.let { it(lecture) }
            }

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((lecture) -> Unit)? = null
    fun setOnItemClickListener(listener: (lecture) -> Unit) {
        onItemClickListener = listener
    }

    private var onItemClickListener2: ((lecture) -> Unit)? = null
    fun setOnItemClickListener2(listener: (lecture) -> Unit) {
        onItemClickListener2 = listener
    }
}