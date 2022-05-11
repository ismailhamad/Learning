package com.example.learning

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.learning.Adapter.showStudentAD
import com.example.learning.Model.users
import com.example.learning.View.Teacher
import com.example.learning.View.teacher.bottomSheetTecherArgs
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_show__student.*
import kotlinx.android.synthetic.main.fragment_show__student.textView38
import kotlinx.android.synthetic.main.fragment_show__student_lecture.*


class Show_StudentLectureFragment : Fragment() {
    lateinit var learningViewModel: LearningViewModel
    val args: Show_StudentLectureFragmentArgs by navArgs()
    lateinit var showStudentAD: showStudentAD
    lateinit var userss:ArrayList<users>

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
        userss = arrayListOf()

        learningViewModel.getUserShowLecture(course.id.toString(), lecture.id.toString())

        learningViewModel.usersLectureMu?.observe(viewLifecycleOwner, Observer {
            if (it == null){
                animationView9.visibility = View.VISIBLE
                textView38.visibility = View.VISIBLE
            }else {
                for (users in it) {
//                users as HashMap<String, users>
                    val data = hashMapOf<String,Any>(
//                    "users" to users(users.id!!,users.name!!,users.lastName!!,users.email!!,null)
                        "id" to users.id!!,
                        "name" to users.name!!,
                        "lastName" to users.lastName!!,
                        "email" to users.email!!,
                    )
                    if (data.get("id").toString() != "") {
                        animationView9.visibility = View.GONE
                        textView38.visibility = View.GONE
                        val items = users(
                            data.get("id").toString(),
                            data.get("name").toString(),
                            data.get("lastName").toString(),
                            data.get("email").toString(), null
                        )
                        userss.add(items)
                        showStudentAD.differ.submitList(userss)
                    }

                }
//                animationView9.visibility = View.VISIBLE
//                textView38.visibility = View.VISIBLE
            }


        })
        setupReceyclview()


    }


    fun setupReceyclview() {
        showStudentAD = showStudentAD()
        rv_showUserL.apply {
            adapter = showStudentAD
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}