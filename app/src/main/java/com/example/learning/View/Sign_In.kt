package com.example.learning.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.learning.Constants.Constants
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningRepository
import com.example.learning.ViewModel.LearningViewModel
import com.example.learning.ViewModel.LearningViewModelProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_sign_in.*

class Sign_In : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val repository = LearningRepository(FirebaseSource(this))
        val viewModelProviderFactory= LearningViewModelProviderFactory(repository)
        learningViewModel = ViewModelProvider(this,viewModelProviderFactory).get(LearningViewModel::class.java)
        sign_In.setOnClickListener {

            if (TextEmail.text.isNotEmpty() && TextPassword.text.isNotEmpty()){
               learningViewModel.Sign_in(findViewById(android.R.id.content),TextEmail.text.toString(),TextPassword.text.toString())

            }else{
                Constants.showSnackBar(findViewById(android.R.id.content),"إملا الحقول المطلوبة",Constants.redColor)
            }
            //finish()
        }
        sign_Up.setOnClickListener {
            val i =Intent(this,Sign_Up::class.java)
            startActivity(i)
        }



    }


}