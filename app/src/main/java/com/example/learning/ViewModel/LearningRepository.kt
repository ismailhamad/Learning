package com.example.learning.ViewModel

import android.net.Uri
import android.view.View
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Model.*

class LearningRepository(
    val firebaseSource: FirebaseSource
) {
    fun Sign_in(view: View, Email: String, Password: String) = firebaseSource.Sign_in(view,Email, Password)
    fun Sign_Up(view: View,password: String, users: users) = firebaseSource.Sign_up(view,password, users)
    fun addcourse(view: View,course: course,imge:Uri?) = firebaseSource.addCourse(view,course,imge)
    fun getCourse() = firebaseSource.getCourse()
   // fun updateCourse(course: course, img: Uri, document: String) = firebaseSource.updateCourse(course, img, document)

    fun deleteCourse(view: View,document: String) = firebaseSource.deleteCourse(view,document)

    //suspend fun addMyCourse(myCourse: myCourse) = firebaseSource.addMyCourse(myCourse)
    fun getMyCourse() = firebaseSource.getMyCourse()

    //suspend fun buyCourseOrNot(id:String) = firebaseSource.BuyCourseOrNot(id)
    suspend fun updateUsers(view:View ,idCourse: String, users: users) =
        firebaseSource.updateUsers(view,idCourse, users)

    fun getLecture(document: String) = firebaseSource.getLecture(document)

    fun addAssignment(view: View,assignment: Assignment, documentCourses:String, documentLecture:String, file:Uri) = firebaseSource.addAssignment(view,assignment,documentCourses,documentLecture,file)
     fun getTeacherCourse(uid: String) = firebaseSource.getTeacherCourse(uid)

    fun addLecture(view: View,lecture: lecture, uriVideo: Uri?, uriFile: Uri?, document: String, code: String) = firebaseSource.addLecture(view,lecture,uriVideo,uriFile,document,code)
}