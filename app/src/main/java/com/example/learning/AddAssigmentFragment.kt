package com.example.learning

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.learning.Constants.Constants
import com.example.learning.Model.Assignment
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.activity_add_assignment.*
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
                learningViewModel.addAssignment(view,Assignment(UUID.randomUUID().toString(),Text_NameAssig.text.toString(),Text_descriptionAssig.text.toString(),fileUri.toString()),idCourse,idlecture, fileUri!!)

            }else{
                Constants.showSnackBar(
                    view, "إملا الحقول المطلوبة",
                   Constants.redColor)
            }


//            if(Text_NameAssig.text.isNotEmpty() && Text_descriptionAssig.text.isNotEmpty()){
//                learningViewModel.addAssignment(
//                    findViewById(android.R.id.content),
//                    Assignment(
//                        UUID.randomUUID().toString(),
//                        ed_nameAssignment.editText!!.text.toString(),
//                        ed_descriptionAssignment.editText!!.text.toString(),
//                        fileUri.toString()
//                    ),"93da687e-4aea-4420-96c6-3f0ed3f222d2",
//                    "1af2fe1b-4df6-4b36-8bb0-7cf06792af0d",
//                    fileUri!!
//                )
//            }else{
//                Constants.showSnackBar(
//                    findViewById(android.R.id.content), "إملا الحقول المطلوبة",
//                    Constants.redColor)
//
//            }
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

}