package com.example.learning.Model

import java.io.Serializable

class course :Serializable{
    var id:String? =""
    var name:String?=""
    var description:String?=""
    var image:String?=""
    var users:ArrayList<Any>?=null
    var lecture:ArrayList<lecture>?=null

  constructor(){

  }
    constructor(id:String?,name:String?,description:String?,image:String?,users:ArrayList<Any>?,lecture:ArrayList<lecture>?){
        this.id=id
        this.name=name
        this.description=description
        this.image=image
        this.users=users
        this.lecture=lecture
    }

    fun getCourseHashMap(): HashMap<String, Any?> {
        val data = hashMapOf<String, Any?>(
            "id" to id,
            "name" to name,
            "description" to description,
            "image" to image,
            "users" to users,
            "lecture" to lecture
        )
        return data
    }
}