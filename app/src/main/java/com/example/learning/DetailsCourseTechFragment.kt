package com.example.learning

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.learning.Adapter.LectureAD
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_details_course_tech.*


class DetailsCourseTechFragment : Fragment(R.layout.fragment_details_course_tech) {

lateinit var learningViewModel: LearningViewModel
lateinit var lectureAD: LectureAD
 val args: DetailsCourseTechFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
learningViewModel =(activity as Teacher).learningViewModel
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView2)
        navBar.visibility=View.GONE
        val course = args.courseTech
       learningViewModel.getLectureTecher(course.id.toString())
        Glide.with(this).load(course.image).into(image_D_T)
        setupReceycleview()
     //   image_D_T.setImageURI(Uri.parse(course.image))
        nameCorseT.text =course.name
        learningViewModel.lectureT!!.observe(viewLifecycleOwner, Observer {
            lectureAD.differ.submitList(it)
        })
        Go_Add_Lect.setOnClickListener {
            val Bundle=Bundle().apply {
                putSerializable("idCourseL",course.id)
            }
   findNavController().navigate(R.id.action_detailsCourseTechFragment_to_addLectureFragment,Bundle)
        }
    }

   fun setupReceycleview(){
     lectureAD = LectureAD()
       rv_lectureTech.apply {
           adapter = lectureAD
           layoutManager = LinearLayoutManager(activity)
       }
   }


}