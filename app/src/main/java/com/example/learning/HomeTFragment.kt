package com.example.learning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.CourseAD
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home_t.*


class HomeTFragment : Fragment(R.layout.fragment_home_t) {
    lateinit var learningViewModel: LearningViewModel
    lateinit var courseAD: CourseAD
    lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Teacher).learningViewModel
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView2)
        navBar.visibility=View.VISIBLE
        auth =Firebase.auth
        learningViewModel.getTeacherCourse(auth.currentUser!!.uid)
        setupReceyclview()
        Go_addCourse.setOnClickListener {
            findNavController().navigate(R.id.action_homeTFragment_to_addCourseFragment)
        }

        learningViewModel.CourseT?.observe(viewLifecycleOwner, Observer {
            courseAD.differ.submitList(it)
        })
        courseAD.setOnItemClickListener {
            val Bundle=Bundle().apply {
                putSerializable("courseTech",it)
            }
findNavController().navigate(R.id.action_homeTFragment_to_detailsCourseTechFragment,Bundle)
        }
    }
 fun setupReceyclview(){
     courseAD= CourseAD()
     recyclerView.apply {
         adapter = courseAD
         layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
     }


 }

}