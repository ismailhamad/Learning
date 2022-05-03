package com.example.learning.Model

import java.io.Serializable

class course :Serializable{
    var id:String? =""
    var namecourse:String?=""
    var description:String?=""
    var image:String?=""
    var idTeacher:String?=""
    var users:List<Any>?=null
    var techer:String?=""
    var time:Long?=0

  constructor(){

  }
    constructor(id:String?,namecourse:String?,description:String?,image:String?,time:Long,users:List<Any>?,techer:String?,idTeacher:String?){
        this.id=id
        this.namecourse=namecourse
        this.description=description
        this.image=image
        this.users=users
        this.techer=techer
        this.idTeacher=idTeacher
        this.time=time
    }

//    fun getCourseHashMap(): HashMap<String, Any?> {
//        val data = hashMapOf<String, Any?>(
//            "id" to id,
//            "name" to name,
//            "description" to description,
//            "image" to image,
//            "users" to users,
//            "lecture" to lecture
//        )
//        return data
//    }
}