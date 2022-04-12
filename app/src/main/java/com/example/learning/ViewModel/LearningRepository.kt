package com.example.learning.ViewModel

import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.course
import com.example.learning.Model.users
import com.example.learning.View.Sign_Up
import com.google.firebase.FirebaseApp

class LearningRepository(
    val firebaseSource: FirebaseSource
) {
    fun Sign_in(Email:String, Password:String) = firebaseSource.Sign_in(Email,Password)
    fun Sign_Up(password:String,users: users) = firebaseSource.Sign_up(password,users)
    fun addcourse(course: course) = firebaseSource.addCourse(course)
     fun getCourse()= firebaseSource.getCourse()
    fun addMyCourse(course: course) = firebaseSource.addMyCourse(course)
    fun getMyCourse()= firebaseSource.getMyCourse()
    fun buyCourseOrNot(id:String) = firebaseSource.BuyCourseOrNot(id)
}