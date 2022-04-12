package com.example.learning.Model

import java.io.Serializable

class course :Serializable{
    var id:String? =""
    var name:String?=""
    var description:String?=""
    var image:String?=""
    var users:HashMap<String, users>?=null
    var lecture:ArrayList<lecture>?=null

  constructor(){

  }
    constructor(id:String?,name:String?,description:String?,image:String?,users:HashMap<String, users>?,lecture:ArrayList<lecture>?){
        this.id=id
        this.name=name
        this.description=description
        this.image=image
        this.users=users
        this.lecture=lecture
    }
}