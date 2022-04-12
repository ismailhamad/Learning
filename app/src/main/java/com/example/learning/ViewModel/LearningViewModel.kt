package com.example.learning.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learning.Model.course
import com.example.learning.Model.users

class LearningViewModel(
val learningRepository: LearningRepository
):ViewModel() {

    var Course: MutableLiveData<List<course>>? = null
    init {
        getCourse()
    }

     fun getCourse() {
   Course = learningRepository.getCourse()
     }


    fun Sign_in(Email:String,Password:String){
      learningRepository.Sign_in(Email,Password)
  }

    fun Sign_Up(users: users){
        learningRepository.Sign_Up(users)
    }

    fun AddCourse(course: course){
        learningRepository.addcourse(course)
    }


}