package com.example.learning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.LectureAD
import com.example.learning.Constants.Constants
import com.example.learning.Model.users
import com.example.learning.View.Student
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_lec.*


class bottomSheetLecFragment : BottomSheetDialogFragment() {
lateinit var learningViewModel: LearningViewModel
lateinit var lectureAD: LectureAD
val args:bottomSheetLecFragmentArgs by navArgs()
    var users:users?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet_lec, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel
        val course = args.myCoursee
        SetupReceycleciew()
        learningViewModel.getLecture(course.id!!)
        learningViewModel.lecture?.observe(viewLifecycleOwner, Observer {
            lectureAD.differ.submitList(it)
        })

        learningViewModel.users?.observe(viewLifecycleOwner, Observer {
            for (item in it){
                users = item

            }
        })


        lectureAD.setOnItemClickListener { itLec ->

            val Bundle = Bundle().apply {
                putSerializable("watch", itLec)
                putSerializable("idCourse",course)
            }

            val indexLec = lectureAD.differ.currentList.indexOf(itLec)
            if (indexLec == 0){
                val idLect = lectureAD.differ.currentList[indexLec].id
                learningViewModel.getUserShowLecture(course.id!!, idLect!!)
                learningViewModel.showUserLecture(users!!, course.id!!, itLec.id!!)

            }else{
                val idLect = lectureAD.differ.currentList[indexLec-1].id
                learningViewModel.getUserShowLecture(course.id!!, idLect!!)
                learningViewModel.showUserLecture(users(users?.id,"a","","",0), course.id!!, itLec.id!!)
            }


            learningViewModel.usersLectureMu?.observe(viewLifecycleOwner, Observer { itlist ->
                itlist.forEach { iii ->
                    if (iii.name+iii.lastName == users?.name+users!!.lastName) {
                        Toast.makeText(activity, "ddddd", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            R.id.action_bottomSheetLecFragment_to_WCourseFragment,
                            Bundle
                        )

                        learningViewModel.showUserLecture(users!!, course.id!!, itLec.id!!)


                    }
                    if(iii?.name=="a") {

                        Constants.showSnackBar(
                            view, "عليك مشاهدة المحاضرات السابقة أولا",
                            Constants.redColor
                        )
                    }
                }

            })

        }
    }

    fun SetupReceycleciew(){
        lectureAD = LectureAD()
        rv_sheet.apply {
            adapter =lectureAD
            layoutManager = LinearLayoutManager(activity)
        }
    }

}