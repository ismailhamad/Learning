package com.example.learning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.learning.Adapter.showStudentAD
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_show__student.*


class Show_StudentLectureFragment : Fragment() {
    lateinit var learningViewModel: LearningViewModel
    val args: Show_StudentLectureFragmentArgs by navArgs()
    lateinit var showStudentAD: showStudentAD
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show__student_lecture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
        val course = args.showCourse
        val lecture = args.showLecture
        //setupReceyclview()
        learningViewModel.getUserShowLecture(course.id.toString(), lecture.id.toString())

//        learningViewModel.usersLectureMu?.observe(viewLifecycleOwner, Observer {
//            if (it==null){
//
//            }else{
//                showStudentAD.differ.submitList(it)
//
//            }
//
//        })


    }


//    fun setupReceyclview() {
//        showStudentAD = showStudentAD()
//        rv_showUser.apply {
//          adapter = showStudentAD
//            layoutManager = GridLayoutManager(activity, 2)
//        }
//    }
}