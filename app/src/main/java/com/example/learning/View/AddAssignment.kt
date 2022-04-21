package com.example.learning.View

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.learning.Constants.Constants
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.Assignment
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningRepository
import com.example.learning.ViewModel.LearningViewModel
import com.example.learning.ViewModel.LearningViewModelProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_assignment.*
import kotlinx.android.synthetic.main.activity_add_lecture.*
import java.util.*

class AddAssignment : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel
    var fileUri: Uri? = null
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_assignment)
        val repository = LearningRepository(FirebaseSource(this))
        val viewModelProviderFactory = LearningViewModelProviderFactory(repository)
        learningViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LearningViewModel::class.java)
        auth = Firebase.auth
        img_AddFileAssignment.setOnClickListener {
            chooseFile()
        }
//        btn_addAssignment.setOnClickListener {
//            if(ed_nameAssignment.editText!!.text.isNotEmpty() && ed_descriptionAssignment.editText!!.text.isNotEmpty()){
//                learningViewModel.addAssignment(
//                    findViewById(android.R.id.content),
//                    Assignment(
//                        UUID.randomUUID().toString(),
//                        ed_nameAssignment.editText!!.text.toString(),
//                        ed_descriptionAssignment.editText!!.text.toString(),
//                        fileUri.toString()
//                    ),"e658a5c6-bcd5-498d-a41f-92f18b82bee9",
//                    "1e56d6c8-3daf-4bfc-91ff-55e67b0a59d9",
//                    fileUri!!
//                )
//            }else{
//                Constants.showSnackBar(
//                    findViewById(android.R.id.content), "إملا الحقول المطلوبة",
//                    Constants.redColor)
//
//            }
//
//        }
        btn_addAssignment.setOnClickListener {
                learningViewModel.userAddAssignment(
                    findViewById(android.R.id.content),
                    users(auth.currentUser!!.uid, "saleem", "mater", auth.currentUser!!.email!!, 0),
                    "e658a5c6-bcd5-498d-a41f-92f18b82bee9",
                    "1e56d6c8-3daf-4bfc-91ff-55e67b0a59d9",
                    "4859bc86-1cda-40b2-bb13-94674e3c4c90",
                    fileUri!!,fileUri.toString()
                )

        }

        btn_updateAssignment.setOnClickListener {
            learningViewModel.updateUserAssignment(
                findViewById(android.R.id.content),
                users(auth.currentUser!!.uid, "saleem", "mater", auth.currentUser!!.email!!, 0),
                "e658a5c6-bcd5-498d-a41f-92f18b82bee9",
                "1e56d6c8-3daf-4bfc-91ff-55e67b0a59d9",
                "4859bc86-1cda-40b2-bb13-94674e3c4c90",
                fileUri!!,fileUri.toString()
            )
        }

        btn_delAssignment.setOnClickListener {
            learningViewModel.deleteUserAssignment(
                findViewById(android.R.id.content),
                "e658a5c6-bcd5-498d-a41f-92f18b82bee9",
                "1e56d6c8-3daf-4bfc-91ff-55e67b0a59d9",
                "4859bc86-1cda-40b2-bb13-94674e3c4c90",
                auth.currentUser!!.uid
            )
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