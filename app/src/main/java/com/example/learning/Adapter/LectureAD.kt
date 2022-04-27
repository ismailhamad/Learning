package com.example.learning.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Model.course
import com.example.learning.Model.lecture
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel

import kotlinx.android.synthetic.main.item_lecture.view.*
import kotlinx.android.synthetic.main.lecture_item.view.*

class LectureAD:RecyclerView.Adapter<LectureAD.ViewHolder>() {

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item){

    }


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
            LayoutInflater.from(parent.context).inflate(R.layout.item_lecture, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lecture = differ.currentList[position]

        holder.itemView.apply {






            if(lecture.seeLecture==false){
                cc_lect.visibility =View.GONE
                check_seee.visibility =View.GONE
            }else{
                name_lect.text = lecture.name
                cc_lect.setCardBackgroundColor(Color.WHITE)
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
}