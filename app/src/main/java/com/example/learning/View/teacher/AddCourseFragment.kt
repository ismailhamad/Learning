package com.example.learning.View.teacher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.learning.Constants.Constants
import com.example.learning.Model.course
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_add_assigment.*
import kotlinx.android.synthetic.main.fragment_add_course.*
import java.util.*
import kotlin.collections.ArrayList


class AddCourseFragment : Fragment(R.layout.fragment_add_course) {
lateinit var learningViewModel: LearningViewModel
lateinit var auth: FirebaseAuth
lateinit var ArrayList:ArrayList<users>
    var imgUrl: Uri? = null
    var nametecher:String?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
//        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView2)
//        navBar.visibility=View.GONE
        ArrayList= arrayListOf()
        auth =Firebase.auth
        learningViewModel.users?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            for (item in it){
                nametecher = item.name
            }
        })
        imageButton6.setOnClickListener {
            findNavController().navigateUp()
        }
        Add_Course.setOnClickListener {
            val user=users("","","","",0)
            ArrayList.add(user)
            if (Text_NameCourse.text.isNotEmpty() && Text_description.text.isNotEmpty() && imgUrl != null){
                learningViewModel.AddCourse(view,course(
                    UUID.randomUUID().toString(),Text_NameCourse.text.toString(),Text_description.text.toString(),
                    imgUrl.toString(),System.currentTimeMillis(),ArrayList as ArrayList<Any>,nametecher,auth.currentUser!!.uid),
                    imgUrl
                )
                cleanText()
            }else{
                Constants.showSnackBar(
                    view, "???????? ???????????? ????????????????",
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

    fun cleanText(){
        Text_NameCourse.text = null
        Text_description.text = null
        image_course.setImageURI(null)
    }


}