package com.example.learning

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.learning.Adapter.showStudentAD
import com.example.learning.Model.users
import com.example.learning.View.ChatActivity
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_show__student.*


class show_StudentFragment : Fragment(R.layout.fragment_show__student) {
lateinit var learningViewModel: LearningViewModel
lateinit var showStudentAD: showStudentAD
    lateinit var auth: FirebaseAuth
    lateinit var userss:ArrayList<users>
val args:show_StudentFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
        val course = args.courseStudent
        auth = Firebase.auth
        userss = arrayListOf()
        setupReceyclview()
        learningViewModel.getStudentrCourse(auth.currentUser!!.uid, course.id.toString())
        learningViewModel.ShowstudentT?.observe(viewLifecycleOwner, Observer {
            for (item in it){
                for (users in item.users!!){
                    users as HashMap<String,users>
                    if (users.get("id").toString()!=""){
                        val items = users(users.get("id").toString(),
                            users.get("name").toString(),
                            users.get("lastName").toString(),
                            users.get("email").toString(),null)
                        userss.add(items)
                        showStudentAD.differ.submitList(userss)
                    }

                }

            }

        })
        showStudentAD.setOnItemClickListener {
            val i =Intent(activity,ChatActivity::class.java)
            i.putExtra("usersT",it)
            startActivity(i)
        }



    }

    fun setupReceyclview(){
        showStudentAD = showStudentAD()
        rv_showS.apply {
            adapter = showStudentAD
            layoutManager =GridLayoutManager(activity,2)
        }
    }

}