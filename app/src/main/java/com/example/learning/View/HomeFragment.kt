package com.example.learning.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.CourseAD
import com.example.learning.Model.course
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.View.Student
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {
  lateinit var learningViewModel: LearningViewModel
  lateinit var ArrayListusers:ArrayList<users>
  lateinit var courseAD: CourseAD
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Student).learningViewModel
ArrayListusers= arrayListOf()
        setupReceycleView()
        var courses = users(null,"ddd","ww","ww","xx")
        ArrayListusers.add(courses)
        cardViewAll.setOnClickListener {
            learningViewModel.AddCourse(course(null,"webb","kmsksmsmsksms",null,ArrayListusers as ArrayList<Any>,null))
            //learningViewModel.AddCourse(course())
        }
        learningViewModel.Course!!.observe(viewLifecycleOwner, Observer {
           courseAD.differ.submitList(it)
        })
    }

    fun setupReceycleView(){
        courseAD =CourseAD()
        rv_Course.apply {
            adapter=courseAD
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        }
    }

}