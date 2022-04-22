package com.example.learning.View

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.course
import com.example.learning.R
import com.example.learning.ViewModel.LearningRepository
import com.example.learning.ViewModel.LearningViewModel
import com.example.learning.ViewModel.LearningViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_add_course.*
import java.util.*

class AddCourse : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel
    var imgUrl: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        val repository = LearningRepository(FirebaseSource(this))
        val viewModelProviderFactory= LearningViewModelProviderFactory(repository)
        learningViewModel = ViewModelProvider(this,viewModelProviderFactory).get(LearningViewModel::class.java)
        img_AddImgCourse.setOnClickListener {
            openMedia()
        }
        btn_addCourse.setOnClickListener {
//            learningViewModel.AddCourse(
//                course(
//                    UUID.randomUUID().toString(),
//                    ed_nameCourse.editText!!.text.toString(),
//                    ed_descriptionCourse.editText!!.text.toString(),
//                    imgUrl.toString(),null,null
//                ),imgUrl!!
//            )

        }

//        btn_updateCourse.setOnClickListener {
//            learningViewModel.updateCourse(
//                course(
//                    UUID.randomUUID().toString(),
//                    ed_nameCourse.editText!!.text.toString(),
//                    ed_descriptionCourse.editText!!.text.toString(),
//                    imgUrl.toString(),null,null
//                ),imgUrl!!,"93da687e-4aea-4420-96c6-3f0ed3f222d2"
//            )
//        }

        btn_delCourse.setOnClickListener {
           // learningViewModel.deleteCourse( findViewById(android.R.id.content),"aa724ec3-382d-4982-88a3-9ced1cd8048a")
        }
    }

    private fun openMedia() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(gallery, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == RESULT_OK && data != null && data.data != null) {
            imgUrl = data.data
            Glide.with(this).load(imgUrl).into(img_AddImgCourse)
        }
    }
}