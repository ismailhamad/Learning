package com.example.learning.ViewModel

import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.course
import com.example.learning.Model.myCourse
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
   suspend fun addMyCourse(myCourse: myCourse) = firebaseSource.addMyCourse(myCourse)
  fun getMyCourse()= firebaseSource.getMyCourse()
   //suspend fun buyCourseOrNot(id:String) = firebaseSource.BuyCourseOrNot(id)
   suspend fun updateUsers(idCourse:String,users: users) = firebaseSource.updateUsers(idCourse,users)
    fun getLecture(document:String)= firebaseSource.getLecture(document)
}