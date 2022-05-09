package com.example.learning.View.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.learning.Adapter.showStudentAD
import com.example.learning.Constants.Constants
import com.example.learning.R
import com.example.learning.ShowUsersLectureArgs
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_show__student.*


class ShowAllUsersLecture : Fragment() {
    lateinit var learningViewModel: LearningViewModel
    lateinit var showStudentAD: showStudentAD
    val args:ShowUsersLectureArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_users_lecture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
        val course = args.showCourse
        val lecture = args.showLecture
        learningViewModel.getUserShowLecture(course.id!!,lecture.id!!)

        learningViewModel.usersLectureMu?.observe(viewLifecycleOwner, Observer {
            showStudentAD.differ.submitList(it)
        })
        setupReceyclview()
    }


    fun setupReceyclview(){
        showStudentAD = showStudentAD()
        rv_showUser.apply {
            adapter = showStudentAD
            layoutManager = GridLayoutManager(activity,2)
        }
    }

}