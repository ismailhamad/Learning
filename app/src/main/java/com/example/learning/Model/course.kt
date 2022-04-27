package com.example.learning.Model

import java.io.Serializable

class course :Serializable{
    var id:String? =""
    var name:String?=""
    var description:String?=""
    var image:String?=""
    var idTeacher:String?=""
    var users:List<Any>?=null
    var techer:String?=""

  constructor(){

  }
    constructor(id:String?,name:String?,description:String?,image:String?,users:List<Any>?,techer:String?,idTeacher:String?){
        this.id=id
        this.name=name
        this.description=description
        this.image=image
        this.users=users
        this.techer=techer
        this.idTeacher=idTeacher
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