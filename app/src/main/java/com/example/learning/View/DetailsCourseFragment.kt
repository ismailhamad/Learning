package com.example.learning.View

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_details_course.*


class DetailsCourseFragment : Fragment(R.layout.fragment_details_course) {
    lateinit var learningViewModel: LearningViewModel
    val args:DetailsCourseFragmentArgs by navArgs()
    lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Student).learningViewModel
    auth = Firebase.auth
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility=View.GONE

        val course = args.course

        titlle.text=course.name
        nameCourseD.text=course.name
        Desc.text=course.description
        countUsers.text=course.users!!.count().toString()
        But_Back.setOnClickListener {
            findNavController().navigate(R.id.action_detailsCourseFragment_to_homeFragment)
        }


        but_Buy.setOnClickListener {
            val users= course.users as HashMap<String, String>
            users.put("id",auth.currentUser!!.uid)
            learningViewModel.AddMyCourse(course)
        }


    }

}