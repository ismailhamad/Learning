package com.example.learning.ViewModel

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning.Model.*
import com.example.learning.Notification.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LearningViewModel(
private val learningRepository: LearningRepository
):ViewModel() {

    var Course: MutableLiveData<List<course>>? = null
    var courseExplore: MutableLiveData<List<course>>? = null
    var users: MutableLiveData<List<users>>? = null
    var usersLectureMu: MutableLiveData<List<users>>? = null
    var CourseT: MutableLiveData<List<course>>? = null
    var ShowstudentT: MutableLiveData<List<course>>? = null
    var search: MutableLiveData<List<course>>? = null
    var MyCourse: MutableLiveData<List<myCourse>>? = null
    var lecture: MutableLiveData<List<lecture>>? = null
    var lectureT: MutableLiveData<List<lecture>>? = null
    var assignment: MutableLiveData<List<Assignment>>? = null
    var userADDASS = MutableLiveData<HashMap<String, Any?>>()
    var BuyNot:Boolean?=null
    var countUser: MutableLiveData<Int>? = null
   init {
     //  getUser()
//   //
     //  getCourse()
      // getMyCourse()
     //  BuyCourseOrNot()
  }

     fun getCourse() {
       Course = MutableLiveData()
   Course = learningRepository.getCourse()
     }
    fun getMyCourse() {
        MyCourse = learningRepository.getMyCourse()
    }

    fun Sign_in(view: View, Email:String, Password:String){
       learningRepository.Sign_in(view,Email,Password)
  }

    fun Sign_Up(view: View,password:String,users: users){
        learningRepository.Sign_Up(view,password,users)
    }

    fun AddCourse(view: View,course: course,imge:Uri?){
        learningRepository.addcourse(view,course,imge)
    }

    fun getTeacherCourse(uid: String) {
        CourseT = learningRepository.getTeacherCourse(uid)
    }

    fun addLecture(view:View,lecture: lecture, uriVideo: Uri?, uriFile: Uri?, document: String, code: String) {
        learningRepository.addLecture(view,lecture,uriVideo,uriFile,document,code)
    }
    fun getStudentrCourse(uid: String,documentCourses: String){
        ShowstudentT =  learningRepository.getStudentrCourse(uid, documentCourses)
    }

//    fun AddMyCourse(myCourse: myCourse) = viewModelScope.launch {
//        learningRepository.addMyCourse(myCourse)
//    }
//    fun BuyCourseOrNot(id:String)=viewModelScope.launch {
//        BuyNot= learningRepository.buyCourseOrNot(id)
//    }


    fun updateUsers(view: View,idCourse:String,users: users) = viewModelScope.launch {
        learningRepository.updateUsers(view,idCourse,users)
    }

    fun getLecture(document:String) {
        lecture = learningRepository.getLecture(document)
    }
    fun getLectureTecher(document:String) {
        lectureT = learningRepository.getLecture(document)
    }
//    fun updateCourse(course: course,img:Uri,document: String) {
//        learningRepository.updateCourse(course,img,document)
//    }

    fun deleteCourse(view:View,document: String) {
        learningRepository.deleteCourse(view,document)
    }

    fun addAssignment(view: View,assignment: Assignment, documentCourses:String, documentLecture:String, file:Uri){
        learningRepository.addAssignment(view,assignment,documentCourses,documentLecture,file)
    }


    fun seeLecture(view: View, document: String, documentLecture: String) {
        learningRepository.seeLecture(view,document,documentLecture)
    }
    fun deleteLecture(view: View, document: String, documentLecture: String) {
        learningRepository.deleteLecture(view, document, documentLecture)
    }
    fun updateLecture(view: View, lecture: lecture, uriVideo: Uri?, uriFile: Uri?, document: String, documentLecture: String, code: String) {
        learningRepository.updateLecture(view, lecture, uriVideo, uriFile, document, documentLecture, code)
    }
    fun deleteMyCourse(view: View,users: users,document:String) {
        learningRepository.deleteMyCourse(view, users, document)
    }
    fun getUser(){
        users = learningRepository.getUser()
    }
    fun getAssignment(documentCourses: String, documentLecture: String) {
        assignment = learningRepository.getAssignment(documentCourses,documentLecture)
    }
    fun userAddAssignment(view: View, users: users, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri, fileString: String){
        learningRepository.userAddAssignment(view, users, documentCourses, documentLecture, documentAssignment, file, fileString)
    }
    fun getuserAddAssigment(documentCourses: String,documentLecture: String, documentAssignment: String){
        userADDASS = learningRepository.getuserAddAssigment(documentCourses, documentLecture, documentAssignment)
    }
    fun updateAssignment(view: View, assignment: Assignment, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri){
        learningRepository.updateAssignment(view, assignment, documentCourses, documentLecture, documentAssignment, file)
    }

    fun deleteAssignment(view: View, documentCourses: String, documentLecture: String, documentAssignment: String){
        learningRepository.deleteAssignment(view, documentCourses, documentLecture, documentAssignment)
    }

    fun searchCourse(text:String){
        search=learningRepository.searchCourse(text)
    }

    fun updateUserAssignment(view: View, documentCourses: String, documentLecture: String, documentAssignment: String, file: Uri, fileString: String) {
        learningRepository.updateUserAssignment(view, documentCourses, documentLecture, documentAssignment, file, fileString)
    }
    fun deleteUserAssignment(view: View, documentCourses: String, documentLecture: String, documentAssignment: String, documentUserAssignment: String) {
        learningRepository.deleteUserAssignment(view, documentCourses, documentLecture, documentAssignment, documentUserAssignment)
    }
    fun sendMessageCourse(chat: Chat, documentMyCourses: String,imge: Uri?) {
        learningRepository.sendMessageCourse(chat, documentMyCourses,imge)
    }
    fun getMessageCourse(documentMyCourses: String)= learningRepository.getMessageCourse(documentMyCourses)
    fun getUserShowLecture(documentCourses: String,documentLecture: String){
        usersLectureMu = learningRepository.getUserShowLecture(documentCourses,documentLecture)
    }
    fun showUserLecture(users: users,documentCourses: String,documentLecture: String) = learningRepository.showUserLecture(users,documentCourses,documentLecture)


   fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d("TAG", "Response: ${Gson().toJson(response)}")
            } else {
                Log.e("TAG", response.errorBody()!!.string())
            }
        } catch(e: Exception) {
            Log.e("TAG", e.toString())
        }
    }

    fun sendMessagePrivate(chat: Chat, documentUsers: String) {
        learningRepository.sendMessagePrivate(chat, documentUsers)
    }
    fun getMessagePrivate(documentUsers: String) = learningRepository.getMessagePrivate(documentUsers)
    fun deleteMessageCourse(documentMyCourses: String,documentChat:String) {
        learningRepository.deleteMessageCourse(documentMyCourses, documentChat)
    }
    fun deleteMessagePrivate(documentUsers: String,documentChat:String) {
        learningRepository.deleteMessagePrivate(documentUsers, documentChat)
    }


    fun addFavorite(view:View,course: course, documentUsers: String) {
        learningRepository.addFavorite(view,course, documentUsers)
    }
    fun deleteFavorite(view:View,documentUsers: String,documentCourses: String) {
        learningRepository.deleteFavorite(view,documentUsers, documentCourses)
    }
    fun getFavorite(documentUsers: String) {
        learningRepository.getFavorite(documentUsers)
    }
    fun getCourseExplore() {
        courseExplore = learningRepository.getCourseExplore()
    }
    fun getCountUserShowLecture(documentCourses: String,documentLecture: String) {
        countUser  = learningRepository.getCountUserShowLecture(documentCourses,documentLecture)
    }
}