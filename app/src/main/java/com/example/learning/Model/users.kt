package com.example.learning.Model

class users {
    var id:String? =""
     var name:String=""
    var lastName:String=""
    var email:String =""
    var numCourse:Int?=0
    constructor()
    constructor(id:String?, name:String, lastName:String, email:String, numCourse: Int?){
        this.id = id
        this.name = name
        this.lastName = lastName
        this.email = email
        this.numCourse=numCourse

    }


}