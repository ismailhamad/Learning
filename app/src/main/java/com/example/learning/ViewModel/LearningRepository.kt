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
    fun deleteCourse(view: View,document: String) = firebaseSource.deleteCourse(view,document)
    fun seeLecture(view: View, document: String, documentLecture: String) = firebaseSource.seeLecture(view,document,documentLecture)
    fun getMyCourse() = firebaseSource.getMyCourse()
   fun deleteLecture(view: View, document: String, documentLecture: String) = firebaseSource.deleteLecture(view,document, documentLecture)
    suspend fun updateUsers(view:View ,idCourse: String, users: users) = firebaseSource.updateUsers(view,idCourse, users)
    fun getLecture(document: String) = firebaseSource.getLecture(document)
    fun addAssignment(view: View,assignment: Assignment, documentCourses:String, documentLecture:String, file:Uri) = firebaseSource.addAssignment(view,assignment,documentCourses,documentLecture,file)
    fun getTeacherCourse(uid: String) = firebaseSource.getTeacherCourse(uid)
    fun addLecture(view: View,lecture: lecture, uriVideo: Uri?, uriFile: Uri?, document: String, code: String) = firebaseSource.addLecture(view,lecture,uriVideo,uriFile,document,code)
    fun updateLecture(view: View, lecture: lecture, uriVideo: Uri?, uriFile: Uri?, document: String, documentLecture: String, code: String) = firebaseSource.updateLecture(view, lecture, uriVideo, uriFile, document, documentLecture, code)
    fun deleteMyCourse(view: View,users: users,document:String) = firebaseSource.deleteMyCourse(view, users, document)
    fun getUser() = firebaseSource.getUser()
    fun getAssignment(documentCourses: String, documentLecture: String) = firebaseSource.getAssignment(documentCourses, documentLecture)
    fun userAddAssignment(view: View, users: users, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri, fileString: String) = firebaseSource.userAddAssignment(view, users, documentCourses, documentLecture, documentAssignment, file, fileString)
    fun getuserAddAssigment(documentCourses: String,documentLecture: String, documentAssignment: String) = firebaseSource.getuserAddAssigment(documentCourses, documentLecture, documentAssignment)
    fun updateUserAssignment(view: View, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri, fileString: String) = firebaseSource.updateUserAssignment(view, documentCourses, documentLecture, documentAssignment, file, fileString)
    fun deleteUserAssignment(view: View, documentCourses: String, documentLecture: String, documentAssignment: String, documentUserAssignment: String) = firebaseSource.deleteUserAssignment(view, documentCourses, documentLecture, documentAssignment, documentUserAssignment)
    fun updateAssignment(view: View, assignment: Assignment, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri) = firebaseSource.updateAssignment(view, assignment, documentCourses, documentLecture, documentAssignment, file)
    fun deleteAssignment(view: View, documentCourses: String, documentLecture: String, documentAssignment: String) = firebaseSource.deleteAssignment(view, documentCourses, documentLecture, documentAssignment)
    fun sendMessageCourse(chat: Chat, documentMyCourses: String,imge: Uri?) =firebaseSource.sendMessageCourse(chat,documentMyCourses,imge)
    fun getMessageCourse(documentMyCourses: String) = firebaseSource.getMessageCourse(documentMyCourses)
    fun getStudentrCourse(uid: String,documentCourses: String) = firebaseSource.getStudentrCourse(uid, documentCourses)
    fun showUserLecture(users: users, documentCourses: String, documentLecture: String) = firebaseSource.showUserLecture(users, documentCourses, documentLecture)
    fun getUserShowLecture(documentCourses: String, documentLecture: String )=firebaseSource.getUserShowLecture(documentCourses, documentLecture)
    fun searchCourse(text:String) = firebaseSource.searchCourse(text)
    fun sendMessagePrivate(chat: Chat, documentUsers: String) = firebaseSource.sendMessagePrivate(chat, documentUsers)
    fun getMessagePrivate(documentUsers: String) = firebaseSource.getMessagePrivate(documentUsers)
    fun deleteMessageCourse(documentMyCourses: String,documentChat:String) = firebaseSource.deleteMessageCourse(documentMyCourses, documentChat)
    fun deleteMessagePrivate(documentUsers: String,documentChat:String) = firebaseSource.deleteMessagePrivate(documentUsers, documentChat)
    fun addFavorite(view:View,course: course, documentUsers: String) = firebaseSource.addFavorite(view,course,documentUsers)
    fun getFavorite(documentUsers: String) = firebaseSource.getFavorite(documentUsers)
    fun deleteFavorite(view:View,documentUsers: String,documentCourses: String) = firebaseSource.deleteFavorite(view,documentUsers,documentCourses)
    fun getCourseExplore() = firebaseSource.getCourseExplore()
    fun getCountUserShowLecture(documentCourses: String,documentLecture: String) = firebaseSource.getCountUserShowLecture(documentCourses,documentLecture)
    fun getAlluserAddAssigment(documentCourses: String, documentLecture: String, documentAssignment: String) = firebaseSource.getAlluserAddAssigment(documentCourses, documentLecture, documentAssignment)
}