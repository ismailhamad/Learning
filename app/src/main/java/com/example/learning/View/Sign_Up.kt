package com.example.learning.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.users
import com.example.learning.R
import com.example.learning.ViewModel.LearningRepository
import com.example.learning.ViewModel.LearningViewModel
import com.example.learning.ViewModel.LearningViewModelProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class Sign_Up : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth
        val repository = LearningRepository(FirebaseSource(this))
        val viewModelProviderFactory= LearningViewModelProviderFactory(repository)
        learningViewModel = ViewModelProvider(this,viewModelProviderFactory).get(LearningViewModel::class.java)
        but_SignUp.setOnClickListener {
           learningViewModel.Sign_Up(Password_SignUp.text.toString(),users(null,TextName_SignUp.text.toString(),TextLName_SignUp.text.toString(),Email_SignUp.text.toString()))
        }

    }
}