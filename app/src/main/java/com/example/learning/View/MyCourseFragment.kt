package com.example.learning.View

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.LectureAD
import com.example.learning.Adapter.MyCourseAD
import com.example.learning.Model.myCourse
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_my_course.*


class MyCourseFragment : Fragment(R.layout.fragment_my_course) {
lateinit var learningViewModel: LearningViewModel
lateinit var myCourseAD: MyCourseAD
lateinit var lectureAD: LectureAD
 var course:myCourse?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel= (activity as Student).learningViewModel

       learningViewModel.getMyCourse()
        setupRecycleView()
        setupRecycleView2()
        learningViewModel.MyCourse!!.observe(viewLifecycleOwner, Observer {
            myCourseAD.differ.submitList(it)
        })
        myCourseAD.setOnItemClickListener {

            textView7.visibility = View.GONE
            learningViewModel.getLecture(it.id.toString())

            course = it
            learningViewModel.lecture?.observe(viewLifecycleOwner, Observer {
                lectureAD.differ.submitList(it)
            })
        }

        lectureAD.setOnItemClickListener {
            val Bundle=Bundle().apply {
                putSerializable("watch",it)
            }
    findNavController().navigate(R.id.action_myCourseFragment_to_WCourseFragment,Bundle)
        }
  if (course !=null){
    Go_Chat.setOnClickListener {
        val Bundle=Bundle().apply {
            putSerializable("chat",course)
        }
        findNavController().navigate(R.id.action_myCourseFragment_to_chatFragment,Bundle)

    }
}


    }

fun setupRecycleView(){
   myCourseAD = MyCourseAD()
    rv_MyCourse.apply {
        adapter = myCourseAD
        layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
    }
}

    fun setupRecycleView2(){
        lectureAD = LectureAD()
        rv_Lect.apply {
            adapter = lectureAD
            layoutManager = LinearLayoutManager(activity)
        }
    }
    companion object{
         var xx:myCourse?=null
    }
}