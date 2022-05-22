package com.example.learning.View.student

import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_details_course.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import java.util.concurrent.TimeUnit


class DetailsCourseFragment : Fragment() {
    lateinit var learningViewModel: LearningViewModel
    val args: DetailsCourseFragmentArgs by navArgs()
    lateinit var auth: FirebaseAuth
    var users: users? = null

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
        learningViewModel = (activity as Student).learningViewModel
        auth = Firebase.auth
        val navBar: DrawerLayout = requireActivity().findViewById(R.id.drawerLayout)
        navBar.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        val gd = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(-0x9e9d9f, -0xececed)
        )


        val course = args.course
        val color = args.color
        gd.setColors(color)
        cardView.background = gd
        cardView.cardElevation = 100f
        imageView4.transitionName = course.image
        nameCourseD.transitionName = course.namecourse
        Glide.with(this).load(course.image).into(imageView4)
        //titlle.text=course.name
        nameCourseD.text = course.namecourse
        Desc.text = course.description
        name_techer.text = course.techer
        countUsers.text = "+${(course.users?.count()?.minus(1))}"

        learningViewModel.favorite?.observe(viewLifecycleOwner, Observer {
            if (it !=null){
                for (item in it){
                    if(item.id == course.id){
                        DrawableCompat.setTint(
                            DrawableCompat.wrap(btn_favorite.getDrawable()),
                            ContextCompat.getColor(requireActivity(), R.color.redd)
                        );
                    }else{
                        DrawableCompat.setTint(
                            DrawableCompat.wrap(btn_favorite.getDrawable()),
                            ContextCompat.getColor(requireActivity(), R.color.white)
                        );
                    }
                }
            }



        })

        But_Back.setOnClickListener {
            findNavController().navigate(R.id.action_detailsCourseFragment_to_homeFragment)
        }
        learningViewModel.users!!.observe(viewLifecycleOwner, Observer { it ->
            for (item in it) {
                users = item
            }
        })


        but_Buy.setOnClickListener {
            users?.let { it1 -> learningViewModel.updateUsers(view, course.id.toString(), it1) }
            SendEmail.sendEmail(
                activity as Student, "learing@gmail.com", users!!.email, "اشتراك بالكورس",
                "I have subscribed to a ${course.namecourse} course \n" +
                        "Course Description \n ${course.description} \n" +
                        "Thank you"
            )
            // learningViewModel.AddMyCourse(myCourse(course.id.toString(),course.name.toString(),course.description,course.image,auth.currentUser!!.uid,course.lecture))

        }

        btn_favorite.setOnClickListener {
            learningViewModel.addFavorite(view, course, users!!.id!!)


        }


    }

}