package com.example.learning.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.R
import com.example.learning.View.student.Student
import com.example.learning.ViewModel.LearningRepository
import com.example.learning.ViewModel.LearningViewModel
import com.example.learning.ViewModel.LearningViewModelProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_add_course.*

class SplashScreen : AppCompatActivity() {
    lateinit var learningViewModel: LearningViewModel
    lateinit var auth: FirebaseAuth
    private lateinit var motionLayout: MotionLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        auth = Firebase.auth

        motionLayout = findViewById(R.id.splash)
        motionLayout.startLayoutAnimation()
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {


            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (auth.currentUser?.uid == null) {
                    val i = Intent(this@SplashScreen, Sign_In::class.java)
                    startActivity(i)
                    finish()
                } else {
                    if (auth.currentUser?.email == "joehamad2060@gmail.com") {
                        val i = Intent(this@SplashScreen, Teacher::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        val i = Intent(this@SplashScreen, Student::class.java)
                        startActivity(i)
                        finish()
                    }

                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

        })


    }
}