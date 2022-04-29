package com.example.learning


import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Adapter.profileCourseAD
import com.example.learning.Model.users
import com.example.learning.View.Student
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_t.*
import kotlinx.android.synthetic.main.fragment_profileuser.*


class profileuserFragment : Fragment() {
lateinit var learningViewModel: LearningViewModel
lateinit var profileCourseAD: profileCourseAD
    var users:users?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profileuser, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Student).learningViewModel
        setupReceycleview()
        learningViewModel.MyCourse?.observe(viewLifecycleOwner, Observer {
            profileCourseAD.differ.submitList(it)
        })

        profileCourseAD.setOnItemClickListener {
            var bundle = Bundle().apply {
                putSerializable("myCoursee",it)
            }
            findNavController().navigate(R.id.action_profileuserFragment_to_bottomSheetLecFragment,bundle)

        }
        learningViewModel.users?.observe(viewLifecycleOwner, Observer {
            for (item in it){
                users = item

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
                profileCourseAD.notifyDataSetChanged()
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val Course = profileCourseAD.differ.currentList[position]

                if (direction == ItemTouchHelper.UP) {
                    users?.let { learningViewModel.deleteMyCourse(view, it,Course.id.toString())

                    }

                }


            }


        }


        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_course_profile)
        }










    }
    fun setupReceycleview(){
        profileCourseAD = profileCourseAD()
        rv_course_profile.apply {
            adapter = profileCourseAD
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        }
    }

}