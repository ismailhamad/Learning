package com.example.learning.View.teacher

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Adapter.CourseAD
import com.example.learning.Adapter.CourseTechAD
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.View.Sign_In
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_t.*
import kotlinx.android.synthetic.main.fragment_home_t.Text_Search


class HomeTFragment : Fragment(R.layout.fragment_home_t) {
    lateinit var learningViewModel: LearningViewModel
    lateinit var CourseTechAD: CourseTechAD
    lateinit var auth: FirebaseAuth
    var idCourse: String = ""
    var user:users?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel

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
                putIntArray("color",Color)
            }

            findNavController().navigate(
                R.id.action_homeTFragment_to_detailsCourseTechFragment,
                Bundle
            )
        }

        learningViewModel.users?.observe(viewLifecycleOwner, Observer {
            for (item in it){
                user = item
            }
            textView8.text = "Hi, Ms ${user?.name}"

        })



        Text_Search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                learningViewModel.searchCourse(query.toString())
                learningViewModel.search?.observe(viewLifecycleOwner, Observer {
                    CourseTechAD.differ.submitList(it)
                })

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "" || newText == null) {
                    learningViewModel.CourseT!!.observe(viewLifecycleOwner, Observer {
                        CourseTechAD.differ.submitList(it)
                    })
                }
                return true
            }

        })
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

        imgBtn_LogOut_t.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, Sign_In::class.java))
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