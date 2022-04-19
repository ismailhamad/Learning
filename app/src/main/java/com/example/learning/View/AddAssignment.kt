package com.example.learning.View

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.learning.Constants.Constants
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.Assignment
import com.example.learning.R
import com.example.learning.ViewModel.LearningRepository
import com.example.learning.ViewModel.LearningViewModel
import com.example.learning.ViewModel.LearningViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_add_assignment.*
import kotlinx.android.synthetic.main.activity_add_lecture.*
import java.util.*

class AddAssignment : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel
    var fileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_assignment)
        val repository = LearningRepository(FirebaseSource(this))
        val viewModelProviderFactory = LearningViewModelProviderFactory(repository)
        learningViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LearningViewModel::class.java)

        img_AddFileAssignment.setOnClickListener {
            chooseFile()
        }
        btn_addAssignment.setOnClickListener {
            if(ed_nameAssignment.editText!!.text.isNotEmpty() && ed_descriptionAssignment.editText!!.text.isNotEmpty()){
                learningViewModel.addAssignment(
                    findViewById(android.R.id.content),
                    Assignment(
                        UUID.randomUUID().toString(),
                        ed_nameAssignment.editText!!.text.toString(),
                        ed_descriptionAssignment.editText!!.text.toString(),
                        fileUri.toString()
                    ),"93da687e-4aea-4420-96c6-3f0ed3f222d2",
                    "1af2fe1b-4df6-4b36-8bb0-7cf06792af0d",
                    fileUri!!
                )
            }else{
                Constants.showSnackBar(
                    findViewById(android.R.id.content), "إملا الحقول المطلوبة",
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
        if (requestCode == 3000 && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
        }
    }
}