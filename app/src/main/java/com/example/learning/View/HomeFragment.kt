package com.example.learning.View

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.CourseAD
import com.example.learning.Model.course
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {
  lateinit var learningViewModel: LearningViewModel
  lateinit var ArrayListusers:HashMap<String, users>
  lateinit var courseAD: CourseAD

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Student).learningViewModel
        val uuid = UUID.randomUUID()
        learningViewModel.getCourse()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility=View.VISIBLE
ArrayListusers= hashMapOf()
        setupReceycleView()
////        var courses = users(null,"ddd","ww","ww")
//        ArrayListusers.add(courses)
        cardViewAll.setOnClickListener {
            learningViewModel.AddCourse(course(uuid.toString(),"webb","kmsksmsmsksms",null,ArrayListusers ,null))
            //learningViewModel.AddCourse(course())
        }

        courseAD.setOnItemClickListener {
//            learningViewModel.BuyCourseOrNot(it.id!!)
//            if (learningViewModel.BuyNot){
//                findNavController().navigate(R.id.action_homeFragment_to_WCourseFragment)
//            }else{
//
//            }

            val Bundle=Bundle().apply {
                putSerializable("course",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_detailsCourseFragment,Bundle)

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