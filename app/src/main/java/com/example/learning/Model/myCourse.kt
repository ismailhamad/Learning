package com.example.learning.Model

import java.io.Serializable

class myCourse :Serializable{
    var id:String? =""
    var name:String?=""
    var description:String?=""
    var image:String?=""
    var users:ArrayList<users>?=null
    var lecture:ArrayList<lecture>?=null
    var idTeacher:String?=""
    constructor(){

    }
    constructor(id:String?,name:String?,description:String?,image:String?,idusers:ArrayList<users>?,idlecture:ArrayList<lecture>?,idTeacher:String?){
        this.id=id
        this.name=name
        this.description=description
        this.image=image
        this.users=idusers
        this.lecture=idlecture
        this.idTeacher=idTeacher
    }

}