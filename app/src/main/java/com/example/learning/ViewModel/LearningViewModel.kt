package com.example.learning.ViewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning.Model.*
import kotlinx.coroutines.launch

class LearningViewModel(
private val learningRepository: LearningRepository
):ViewModel() {

    var Course: MutableLiveData<List<course>>? = null
    var CourseT: MutableLiveData<List<course>>? = null
    var MyCourse: MutableLiveData<List<myCourse>>? = null
    var lecture: MutableLiveData<List<lecture>>? = null
    var lectureT: MutableLiveData<List<lecture>>? = null
    var BuyNot:Boolean?=null
   init {
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

    fun Sign_in(Email:String,Password:String){
      learningRepository.Sign_in(Email,Password)
  }

    fun Sign_Up(password:String,users: users){
        learningRepository.Sign_Up(password,users)
    }

    fun AddCourse(course: course,imge:Uri?){
        learningRepository.addcourse(course,imge)
    }

    fun getTeacherCourse(uid: String) {
        CourseT = learningRepository.getTeacherCourse(uid)
    }

    fun addLecture(lecture: lecture, uriVideo: Uri?, uriFile: Uri?, document: String, code: String) {
        learningRepository.addLecture(lecture,uriVideo,uriFile,document,code)
    }

//    fun AddMyCourse(myCourse: myCourse) = viewModelScope.launch {
//        learningRepository.addMyCourse(myCourse)
//    }
//    fun BuyCourseOrNot(id:String)=viewModelScope.launch {
//        BuyNot= learningRepository.buyCourseOrNot(id)
//    }


    fun updateUsers(idCourse:String,users: users) = viewModelScope.launch {
        learningRepository.updateUsers(idCourse,users)
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

    fun deleteCourse(document: String) {
        learningRepository.deleteCourse(document)
    }

    fun addAssignment(assignment: Assignment, documentCourses:String, documentLecture:String, file:Uri){
        learningRepository.addAssignment(assignment,documentCourses,documentLecture,file)
    }
//    fun updateLecture(lecture: lecture, idcourse: String?){
//        learningRepository.updateLecture(lecture,idcourse)
//    }




}