package com.example.learning.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learning.Adapter.CourseAD
import com.example.learning.Model.course
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeFragment : Fragment(R.layout.fragment_home) {
  lateinit var learningViewModel: LearningViewModel
  lateinit var ArrayListusers:ArrayList<users>
  lateinit var courseAD: CourseAD
lateinit var auth: FirebaseAuth
  var serach:String?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Student).learningViewModel
        val uuid = UUID.randomUUID()
       learningViewModel.getCourse()
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility=View.VISIBLE
        ArrayListusers= arrayListOf()
        auth =Firebase.auth
        setupReceycleView()
        imageButton.setOnClickListener {
            startActivity(Intent(context, AddAssignment::class.java))
        }
        learningViewModel.Course!!.observe(viewLifecycleOwner, Observer {
            courseAD.differ.submitList(it)
        })
        learningViewModel.users?.observe(viewLifecycleOwner, Observer {
            for (item in it){
                name_user.text="Hi,${item.name}"
            }
        })

        courseAD.setOnItemClickListener {


            val Bundle=Bundle().apply {
                putSerializable("course",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_detailsCourseFragment,Bundle)


        }

        Text_Search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                    learningViewModel.searchCourse(query.toString()).observe(viewLifecycleOwner,
                        Observer {itt->
                            courseAD.differ.submitList(itt)
                        })


               return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText=="" || newText ==null){
                    learningViewModel.Course!!.observe(viewLifecycleOwner, Observer {
                        courseAD.differ.submitList(it)
                    })
                }
                return true
            }

        })






    }




    fun setupReceycleView(){
        courseAD =CourseAD()
        rv_Course.apply {
            adapter=courseAD
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        }
    }

}