package com.example.learning.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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
import kotlinx.android.synthetic.main.activity_sign_up.*

class Sign_Up : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth
        val repository = LearningRepository(FirebaseSource(this))
        val viewModelProviderFactory = LearningViewModelProviderFactory(repository)
        learningViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LearningViewModel::class.java)
        but_SignUp.setOnClickListener {
            if (Password_SignUp.text.isNotEmpty() && TextName_SignUp.text.isNotEmpty() && Email_SignUp.text.isNotEmpty() && TextLName_SignUp.text.isNotEmpty()) {
                signUp()
            } else {
                Constants.showSnackBar(
                    findViewById(android.R.id.content), "إملا الحقول المطلوبة",
                    Constants.redColor
                )
            }
        }

    }

    fun signUp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Privacy Policy")
        builder.setMessage("Whether you have an iOS or Android app, you must keep your mobile application compliant with an app privacy policy. Keep reading to find out what they are, whether you need one, and the requirements for different platforms")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->
            learningViewModel.Sign_Up(
                findViewById(android.R.id.content),
                Password_SignUp.text.toString(),
                users(
                    null,
                    TextName_SignUp.text.toString(),
                    TextLName_SignUp.text.toString(),
                    Email_SignUp.text.toString(),
                    0
                )
            )
            dialog.cancel()

        }

        builder.setNegativeButton(
            "No"
        ) { dialog, which ->
            Constants.showSnackBar(
                findViewById(android.R.id.content), "يجب الموافقة على شروط سياسة الاستخدام",
                Constants.redColor
            )
            dialog.cancel()
        }

        val alertDialog = builder.create()

        alertDialog.show()
    }

}