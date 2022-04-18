package com.example.learning.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeFragment : Fragment(R.layout.fragment_home) {
  lateinit var learningViewModel: LearningViewModel
  lateinit var ArrayListusers:ArrayList<users>
  lateinit var courseAD: CourseAD
lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Student).learningViewModel
        val uuid = UUID.randomUUID()
       learningViewModel.getCourse()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility=View.VISIBLE
        ArrayListusers= arrayListOf()
        auth =Firebase.auth
        setupReceycleView()
        imageButton.setOnClickListener {
            startActivity(Intent(context, AddLecture::class.java))
        }
//        cardViewAll.setOnClickListener {
//            var courses = users(null,"","","",0)
//            ArrayListusers.add(courses)
//            learningViewModel.AddCourse(course(uuid.toString(),"hhh","kmsksmsmsksms",null,ArrayListusers as ArrayList<Any>,null))
//            //learningViewModel.AddCourse(course())
//        }

        courseAD.setOnItemClickListener {
        val i = Intent()
            i.putExtra("idCourse",it.id)
//         learningViewModel.BuyCourseOrNot(it.id!!)
//
//        if (learningViewModel.BuyNot!!){
//            findNavController().navigate(R.id.action_homeFragment_to_WCourseFragment)
//
//        }else{
//
//
//        }

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