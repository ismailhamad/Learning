package com.example.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.learning.Constants.Constants
import com.example.learning.Model.course
import com.example.learning.Model.users
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_course.*
import kotlinx.android.synthetic.main.fragment_add_course.*
import java.util.*
import kotlin.collections.ArrayList


class AddCourseFragment : Fragment(R.layout.fragment_add_course) {
lateinit var learningViewModel: LearningViewModel
lateinit var auth: FirebaseAuth
lateinit var ArrayList:ArrayList<users>
    var imgUrl: Uri? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView2)
        navBar.visibility=View.GONE
        ArrayList= arrayListOf()
        auth =Firebase.auth
        Add_Course.setOnClickListener {
            val user=users(null,"","","",null)
            ArrayList.add(user)
            if (Text_NameCourse.text.isNotEmpty() && Text_description.text.isNotEmpty() && imgUrl != null){
                learningViewModel.AddCourse(view,course(
                    UUID.randomUUID().toString(),Text_NameCourse.text.toString(),Text_description.text.toString(),
                    imgUrl.toString(),ArrayList as ArrayList<Any>,null,auth.currentUser!!.uid),
                    imgUrl
                )
            }else{
                Constants.showSnackBar(
                    view, "إملا الحقول المطلوبة",
                    Constants.redColor
                )
            }


        }


        image_course.setOnClickListener {
            openMedia()
        }

    }

    private fun openMedia() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            imgUrl = data.data
            image_course.setImageURI(imgUrl)
            //Glide.with(this).load(imgUrl).into(img_AddImgCourse)
        }
    }


}