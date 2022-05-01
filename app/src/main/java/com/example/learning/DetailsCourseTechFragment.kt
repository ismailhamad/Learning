package com.example.learning

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learning.Adapter.LectureAD
import com.example.learning.Adapter.LectureTecAD
import com.example.learning.Model.course
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details_course_tech.*


class DetailsCourseTechFragment : Fragment(R.layout.fragment_details_course_tech) {

lateinit var learningViewModel: LearningViewModel
lateinit var LectureTecAD: LectureTecAD
lateinit var course:course
 val args: DetailsCourseTechFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
learningViewModel =(activity as Teacher).learningViewModel
//        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView2)
//        navBar.visibility=View.GONE
         course = args.courseTech
       learningViewModel.getLectureTecher(course.id.toString())
        Glide.with(this).load(course.image).into(image_D_T)
        setupReceycleview()
     //   image_D_T.setImageURI(Uri.parse(course.image))
        nameCorseT.text =course.namecourse
        learningViewModel.lectureT!!.observe(viewLifecycleOwner, Observer {
            LectureTecAD.differ.submitList(it)
        })
        Go_Add_Lect.setOnClickListener {
            val Bundle=Bundle().apply {
                putSerializable("idCourseL",course)

            }
   findNavController().navigate(R.id.action_detailsCourseTechFragment_to_addLectureFragment,Bundle)
        }
        LectureTecAD.setOnItemClickListener {
            val Bundle=Bundle().apply {
                putSerializable("detailsLecture",it)
                putString("imageCourse",course.image)
                putSerializable("idcourse",course)

            }
            findNavController().navigate(R.id.action_detailsCourseTechFragment_to_details_LectureTechFragment,Bundle)
        }

        LectureTecAD.setOnItemClickListener2 {
            learningViewModel.seeLecture(view,course.id.toString(),it.id.toString())
            //Toast.makeText(activity, "ddddddddd", Toast.LENGTH_SHORT).show()
        }

        val itemTouchHelperCallback=object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val lecture=LectureTecAD.differ.currentList[position]
             //   viewModel.deleteNote(article)
               if (direction == ItemTouchHelper.LEFT){
                   val Bundle= Bundle().apply {
                       putSerializable("updateLecture",lecture)
                       putString("idcoursee",course.id)
                   }
                   findNavController().navigate(R.id.action_detailsCourseTechFragment_to_updateLectureFragment,Bundle)
               }else if (direction == ItemTouchHelper.RIGHT){
                   learningViewModel.deleteLecture(view,course.id.toString(),lecture.id.toString())

               }


            }

        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_lectureTech)
        }


        Go_showStudent.setOnClickListener {
            val Bundle = Bundle().apply {
                putSerializable("courseStudent",course)
            }
            findNavController().navigate(R.id.action_detailsCourseTechFragment_to_show_StudentFragment,Bundle)
        }



















    }




   fun setupReceycleview(){
       LectureTecAD = LectureTecAD()
       rv_lectureTech.apply {
           adapter = LectureTecAD
           layoutManager = LinearLayoutManager(activity)
       }
   }


}