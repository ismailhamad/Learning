package com.example.learning.View.teacher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.learning.Model.Assignment
import com.example.learning.R
import com.example.learning.View.Teacher
import com.example.learning.ViewModel.LearningViewModel
import kotlinx.android.synthetic.main.fragment_add_course.*
import kotlinx.android.synthetic.main.fragment_update_assigment.*


class updateAssigmentFragment : Fragment(R.layout.fragment_update_assigment) {
    var fileUri: Uri? = null
lateinit var learningViewModel: LearningViewModel
val args:updateAssigmentFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        learningViewModel = (activity as Teacher).learningViewModel
        val assigment=args.assigmentt
        val idcourse=args.idcoursee
        val idlecture=args.idlecturee
        Text_NameUpAssig.append(assigment.name.toString())
        Text_descriptionUpAssig.append(assigment.description.toString())
        Text_pdfUpAssig.setOnClickListener {
            chooseFile()
        }
        imageButton120.setOnClickListener {
            findNavController().navigateUp()
        }
        update_Assigment.setOnClickListener {
            fileUri?.let { it1 ->
                learningViewModel.updateAssignment(view,Assignment(assigment.id!!,Text_NameUpAssig.text.toString(),Text_descriptionUpAssig.text.toString(),it1.toString()),idcourse,idlecture,assigment.id.toString(),
                    it1
                )
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
            Text_pdfUpAssig.append(fileUri.toString())
        }
    }

}