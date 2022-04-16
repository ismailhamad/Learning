package com.example.learning.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning.Model.course
import com.example.learning.Model.lecture
import com.example.learning.Model.myCourse
import com.example.learning.Model.users
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class LearningViewModel(
private val learningRepository: LearningRepository
):ViewModel() {

    var Course: MutableLiveData<List<course>>? = null
    var MyCourse: MutableLiveData<List<myCourse>>? = null
    var lecture: MutableLiveData<List<lecture>>? = null

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

    fun AddCourse(course: course){
        learningRepository.addcourse(course)
    }

    fun AddMyCourse(myCourse: myCourse) = viewModelScope.launch {
        learningRepository.addMyCourse(myCourse)
    }
//    fun BuyCourseOrNot(id:String)=viewModelScope.launch {
//        BuyNot= learningRepository.buyCourseOrNot(id)
//    }


    fun updateUsers(idCourse:String,users: users) = viewModelScope.launch {
        learningRepository.updateUsers(idCourse,users)
    }

    fun getLecture(document:String) {
        lecture = learningRepository.getLecture(document)
    }



}