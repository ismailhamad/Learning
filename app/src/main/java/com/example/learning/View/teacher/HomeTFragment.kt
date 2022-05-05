package com.example.learning.View.teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Adapter.CourseAD
import com.example.learning.Adapter.CourseTechAD
import com.example.learning.R
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home_t.*


class HomeTFragment : Fragment(R.layout.fragment_home_t) {
    lateinit var learningViewModel: LearningViewModel
    lateinit var CourseTechAD: CourseTechAD
    lateinit var auth: FirebaseAuth
    var idCourse: String = ""




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
//        val navBar: BottomNavigationView =
//            requireActivity().findViewById(R.id.bottomNavigationView2)
//        navBar.visibility = View.VISIBLE
        auth = Firebase.auth
        learningViewModel.getTeacherCourse(auth.currentUser!!.uid)
        setupReceyclview()
        Go_addCourse.setOnClickListener {
            findNavController().navigate(R.id.action_homeTFragment_to_addCourseFragment)
        }

        learningViewModel.CourseT?.observe(viewLifecycleOwner, Observer {
            CourseTechAD.differ.submitList(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
        CourseTechAD.setOnItemClickListener { course,Color ->
            val Bundle = Bundle().apply {
                putSerializable("courseTech", course)
            }

            findNavController().navigate(
                R.id.action_homeTFragment_to_detailsCourseTechFragment,
                Bundle
            )
        }



        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            ItemTouchHelper.UP
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val Course = CourseTechAD.differ.currentList[position]
                //   viewModel.deleteNote(article)
                if (direction == ItemTouchHelper.UP) {
                    learningViewModel.deleteCourse(view, Course.id.toString())

                }

            }


        }


        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recyclerView)
        }
    }
 fun setupReceyclview(){
     CourseTechAD= CourseTechAD()
     recyclerView.apply {
         adapter = CourseTechAD
         layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

     }


 }

}