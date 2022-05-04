package com.example.learning.View.teacher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.learning.Constants.Constants
import com.example.learning.Model.Assignment
import com.example.learning.Model.NotificationData
import com.example.learning.Model.PushNotification
import com.example.learning.Model.users
import com.example.learning.R

import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_add_assigment.*
import kotlinx.android.synthetic.main.fragment_add_lecture.*
import java.util.*


class AddAssigmentFragment : Fragment(R.layout.fragment_add_assigment) {
lateinit var learningViewModel: LearningViewModel
    var fileUri: Uri? = null
    val args:AddAssigmentFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
        val idlecture = args.idlectureAssi
        val idCourse = args.idCourseAssi
        Text_pdfAssig.setOnClickListener {
            chooseFile()
        }

        Add_Assigment.setOnClickListener {
            if(Text_NameAssig.text.toString().isNotEmpty() && Text_descriptionAssig.text.toString().isNotEmpty()){
                learningViewModel.addAssignment(view,Assignment(UUID.randomUUID().toString(),Text_NameAssig.text.toString(),Text_descriptionAssig.text.toString(),fileUri.toString()),idCourse.id.toString(),idlecture, fileUri!!)
                for (users in idCourse.users!!){
                    users as HashMap<String, users>
                    if (users.get("id").toString()!=""){
                        val topic = "/topics/${users.get("id").toString()}"
                        PushNotification(
                            NotificationData( "a new assignment","A new assignment has been added to the ${idCourse.namecourse} course"),
                            topic).also {
                           learningViewModel.sendNotification(it)
                        }
                    }

                }
                cleanText()

            }else{
                Constants.showSnackBar(
                    view, "إملا الحقول المطلوبة",
                   Constants.redColor)
            }



        }

    }

    private fun chooseFile() {
        val intent = Intent()
        intent.type = "application/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 3000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3000 && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
            Text_pdfAssig.append(fileUri.toString())
        }
    }
    fun cleanText(){
        Text_NameAssig.text = null
        Text_descriptionAssig.text = null
        Text_pdfAssig.text = null
    }


}