package com.example.learning.View.student


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learning.Adapter.profileCourseAD
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.View.Sign_In
import com.example.learning.View.Sign_Up
import com.example.learning.ViewModel.LearningViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profileuser.*


class profileuserFragment : Fragment() {
lateinit var learningViewModel: LearningViewModel
lateinit var profileCourseAD: profileCourseAD
    lateinit var auth: FirebaseAuth
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
        auth = Firebase.auth
        setupReceycleview()
        learningViewModel.MyCourse?.observe(viewLifecycleOwner, Observer {
            profileCourseAD.differ.submitList(it)
            rv_course_profile.adapter?.notifyDataSetChanged()
        })

        imageButton4.setOnClickListener {
            findNavController().navigate(R.id.action_profileuserFragment_to_homeFragment)
        }
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
                    users?.let { learningViewModel.deleteMyCourse(view, it,Course.id!!)

                    }

                }


            }


        }


        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rv_course_profile)
        }

        imgBtn_LogOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity,Sign_In::class.java))
            requireActivity().finish()

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