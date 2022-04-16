package com.example.learning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learning.View.Student
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class WCourseFragment : Fragment(R.layout.fragment_w_course) {
lateinit var learningViewModel: LearningViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Student).learningViewModel
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility=View.GONE
    }

}