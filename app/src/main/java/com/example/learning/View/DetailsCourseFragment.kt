package com.example.learning.View

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.learning.Model.SendEmail
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_details_course.*
import java.util.concurrent.TimeUnit


class DetailsCourseFragment : Fragment() {
    lateinit var learningViewModel: LearningViewModel
    val args:DetailsCourseFragmentArgs by navArgs()
    lateinit var auth: FirebaseAuth
    var users: users?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_course, container, false)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel =(activity as Student).learningViewModel
    auth = Firebase.auth
        val navBar: DrawerLayout = requireActivity().findViewById(R.id.drawerLayout)
        navBar.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        val course = args.course
        imageView4.transitionName = course.image
        nameCourseD.transitionName = course.namecourse
Glide.with(this).load(course.image).into(imageView4)
        //titlle.text=course.name
        nameCourseD.text=course.namecourse
        Desc.text=course.description
        name_techer.text =course.techer
        countUsers.text="+${(course.users?.count()?.minus(1))}"

        But_Back.setOnClickListener {
            findNavController().navigate(R.id.action_detailsCourseFragment_to_homeFragment)
        }
learningViewModel.users!!.observe(viewLifecycleOwner, Observer { it->
    for (item in it){
        users = item
    }
})


        but_Buy.setOnClickListener {
            users?.let { it1 -> learningViewModel.updateUsers(view,course.id.toString(), it1) }
            SendEmail.sendEmail(
                activity as Student,"learing@gmail.com","shroud123shroud@gmail.com","اشتراك بالكورس",
            "<h1>learing Course</h1>" +
                    "<p>" +
                    "<b>اشتراك بالكورس<br>" +
                    "<b>وصف الكورس<br>" +
                    "<b>Email: </b>'shroud123shroud@gmail.com'<br>" +
                    "</p>")
           // learningViewModel.AddMyCourse(myCourse(course.id.toString(),course.name.toString(),course.description,course.image,auth.currentUser!!.uid,course.lecture))

        }


    }

}