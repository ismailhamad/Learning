package com.example.learning.ViewModel

import android.net.Uri
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.Assignment
import com.example.learning.Model.course
import com.example.learning.Model.myCourse
import com.example.learning.Model.users
import com.example.learning.View.Sign_Up
import com.google.firebase.FirebaseApp

class LearningRepository(
    val firebaseSource: FirebaseSource
) {
    fun Sign_in(Email: String, Password: String) = firebaseSource.Sign_in(Email, Password)
    fun Sign_Up(password: String, users: users) = firebaseSource.Sign_up(password, users)
    fun addcourse(course: course, img: Uri) = firebaseSource.addCourse(course, img)
    fun getCourse() = firebaseSource.getCourse()
    fun updateCourse(course: course, img: Uri, document: String) =
        firebaseSource.updateCourse(course, img, document)

    fun deleteCourse(document: String) = firebaseSource.deleteCourse(document)

    //suspend fun addMyCourse(myCourse: myCourse) = firebaseSource.addMyCourse(myCourse)
    fun getMyCourse() = firebaseSource.getMyCourse()

    //suspend fun buyCourseOrNot(id:String) = firebaseSource.BuyCourseOrNot(id)
    suspend fun updateUsers(idCourse: String, users: users) =
        firebaseSource.updateUsers(idCourse, users)

    fun getLecture(document: String) = firebaseSource.getLecture(document)

    fun addAssignment(assignment: Assignment, documentCourses:String, documentLecture:String, file:Uri) = firebaseSource.addAssignment(assignment,documentCourses,documentLecture,file)


}