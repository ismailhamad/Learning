package com.example.learning.ViewModel

import android.app.Activity
import android.net.Uri
import android.view.View
import com.example.learning.Firebase.FirebaseSource
import com.example.learning.Firebase.Student.FirebaseSourceAssigmentST
import com.example.learning.Firebase.Student.FirebaseSourceCourseST
import com.example.learning.Firebase.Student.FirebaseSourceLectureST
import com.example.learning.Firebase.Teacher.FirebaseSourceAssigmentTR
import com.example.learning.Firebase.Teacher.FirebaseSourceCourseTR
import com.example.learning.Firebase.Teacher.FirebaseSourceLectureTR

import com.example.learning.Model.*

class LearningRepository(
    val firebaseSource: FirebaseSource,
    val activity: Activity
) {
    val firebaseSourceCourseTR = FirebaseSourceCourseTR(activity)
    val firebaseSourceCourseST = FirebaseSourceCourseST(activity)
    val firebaseSourceLectureTR = FirebaseSourceLectureTR(activity)
    val firebaseSourceLectureST = FirebaseSourceLectureST(activity)
    val firebaseSourceAssigmentTR = FirebaseSourceAssigmentTR(activity)
    val firebaseSourceAssigmentST = FirebaseSourceAssigmentST(activity)
    fun Sign_in(view: View, Email: String, Password: String) = firebaseSource.Sign_in(view,Email, Password)
    fun Sign_Up(view: View,password: String, users: users) = firebaseSource.Sign_up(view,password, users)
    fun addcourse(view: View,course: course,imge:Uri?) = firebaseSourceCourseTR.addCourse(view,course,imge)
    fun getCourse() = firebaseSourceCourseST.getCourse()
    fun deleteCourse(view: View,document: String) = firebaseSourceCourseTR.deleteCourse(view,document)
    fun seeLecture(view: View, document: String, documentLecture: String) = firebaseSourceLectureTR.seeLecture(view,document,documentLecture)
    fun getMyCourse() = firebaseSourceCourseST.getMyCourse()
   fun deleteLecture(view: View, document: String, documentLecture: String) = firebaseSourceLectureTR.deleteLecture(view,document, documentLecture)
    suspend fun updateUsers(view:View ,idCourse: String, users: users) = firebaseSourceCourseST.updateUsers(view,idCourse, users)
    fun getLecture(document: String) = firebaseSourceLectureST.getLecture(document)
    fun addAssignment(view: View,assignment: Assignment, documentCourses:String, documentLecture:String, file:Uri) = firebaseSourceAssigmentTR.addAssignment(view,assignment,documentCourses,documentLecture,file)
    fun getTeacherCourse(uid: String) = firebaseSourceCourseTR.getTeacherCourse(uid)
    fun addLecture(view: View,lecture: lecture, uriVideo: Uri?, uriFile: Uri?, document: String, code: String) = firebaseSourceLectureTR.addLecture(view,lecture,uriVideo,uriFile,document,code)
    fun updateLecture(view: View, lecture: lecture, uriVideo: Uri?, uriFile: Uri?, document: String, documentLecture: String, code: String) = firebaseSourceLectureTR.updateLecture(view, lecture, uriVideo, uriFile, document, documentLecture, code)
    fun deleteMyCourse(view: View,users: users,document:String) = firebaseSourceCourseST.deleteMyCourse(view, users, document)
    fun getUser() = firebaseSource.getUser()
    fun getAssignment(documentCourses: String, documentLecture: String) = firebaseSourceAssigmentST.getAssignment(documentCourses, documentLecture)
    fun userAddAssignment(view: View, users: users, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri, fileString: String) = firebaseSourceAssigmentST.userAddAssignment(view, users, documentCourses, documentLecture, documentAssignment, file, fileString)
    fun getuserAddAssigment(documentCourses: String,documentLecture: String, documentAssignment: String) = firebaseSourceAssigmentTR.getuserAddAssigment(documentCourses, documentLecture, documentAssignment)
    fun updateUserAssignment(view: View, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri, fileString: String) = firebaseSourceAssigmentST.updateUserAssignment(view, documentCourses, documentLecture, documentAssignment, file, fileString)
    fun deleteUserAssignment(view: View, documentCourses: String, documentLecture: String, documentAssignment: String, documentUserAssignment: String) = firebaseSourceAssigmentST.deleteUserAssignment(view, documentCourses, documentLecture, documentAssignment, documentUserAssignment)
    fun updateAssignment(view: View, assignment: Assignment, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri) = firebaseSourceAssigmentTR.updateAssignment(view, assignment, documentCourses, documentLecture, documentAssignment, file)
    fun deleteAssignment(view: View, documentCourses: String, documentLecture: String, documentAssignment: String) = firebaseSourceAssigmentTR.deleteAssignment(view, documentCourses, documentLecture, documentAssignment)
    fun sendMessageCourse(chat: Chat, documentMyCourses: String,imge: Uri?) =firebaseSource.sendMessageCourse(chat,documentMyCourses,imge)
    fun getMessageCourse(documentMyCourses: String) = firebaseSource.getMessageCourse(documentMyCourses)
    fun getStudentrCourse(uid: String,documentCourses: String) = firebaseSourceCourseST.getStudentrCourse(uid, documentCourses)
    fun showUserLecture(users: users, documentCourses: String, documentLecture: String) = firebaseSourceLectureST.showUserLecture(users, documentCourses, documentLecture)
    fun getUserShowLecture(documentCourses: String, documentLecture: String )=firebaseSourceLectureST.getUserShowLecture(documentCourses, documentLecture)
    fun searchCourse(text:String) = firebaseSourceCourseST.searchCourse(text)
    fun sendMessagePrivate(chat: Chat, documentUsers: String) = firebaseSource.sendMessagePrivate(chat, documentUsers)
    fun getMessagePrivate(documentUsers: String) = firebaseSource.getMessagePrivate(documentUsers)
    fun deleteMessageCourse(documentMyCourses: String,documentChat:String) = firebaseSource.deleteMessageCourse(documentMyCourses, documentChat)
    fun deleteMessagePrivate(documentUsers: String,documentChat:String) = firebaseSource.deleteMessagePrivate(documentUsers, documentChat)
    fun addFavorite(view:View,course: course, documentUsers: String) = firebaseSourceCourseST.addFavorite(view,course,documentUsers)
    fun getFavorite(documentUsers: String) = firebaseSourceCourseST.getFavorite(documentUsers)
    fun deleteFavorite(view:View,documentUsers: String,documentCourses: String) = firebaseSourceCourseST.deleteFavorite(view,documentUsers,documentCourses)
    fun getCourseExplore() = firebaseSourceCourseST.getCourseExplore()
    fun getCountUserShowLecture(documentCourses: String,documentLecture: String) = firebaseSourceLectureTR.getCountUserShowLecture(documentCourses,documentLecture)
    fun getAlluserAddAssigment(documentCourses: String, documentLecture: String, documentAssignment: String) = firebaseSourceAssigmentTR.getAlluserAddAssigment(documentCourses, documentLecture, documentAssignment)
}