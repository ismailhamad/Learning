package com.example.learning.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learning.Model.course
import com.example.learning.Model.users

class LearningViewModel(
private val learningRepository: LearningRepository
):ViewModel() {

    var Course: MutableLiveData<List<course>>? = null
    var MyCourse: MutableLiveData<List<course>>? = null
    var BuyNot: Boolean= false
//    init {
//        getCourse()
//        getMyCourse()
//    }

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

    fun AddMyCourse(course: course){
        learningRepository.addMyCourse(course)
    }
    fun BuyCourseOrNot(id:String) {
        BuyNot = learningRepository.buyCourseOrNot(id)
    }




}