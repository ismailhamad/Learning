package com.example.learning.ViewModel

import android.net.Uri
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning.Model.*
import kotlinx.coroutines.launch

class LearningViewModel(
private val learningRepository: LearningRepository
):ViewModel() {

    var Course: MutableLiveData<List<course>>? = null
    var users: MutableLiveData<List<users>>? = null
    var CourseT: MutableLiveData<List<course>>? = null
    var MyCourse: MutableLiveData<List<myCourse>>? = null
    var lecture: MutableLiveData<List<lecture>>? = null
    var lectureT: MutableLiveData<List<lecture>>? = null
    var assignment: MutableLiveData<List<Assignment>>? = null
    var BuyNot:Boolean?=null

   init {
     //  getUser()
//   //
     //  getCourse()
      // getMyCourse()
     //  BuyCourseOrNot()
  }

     fun getCourse() {
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
//    fun updateLecture(lecture: lecture, idcourse: String?){
//        learningRepository.updateLecture(lecture,idcourse)
//    }

    fun seeLecture(view: View, document: String, documentLecture: String) {
        learningRepository.seeLecture(view,document,documentLecture)
    }
    fun deleteLecture(view: View, document: String, documentLecture: String) {
        learningRepository.seeLecture(view, document, documentLecture)
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




}