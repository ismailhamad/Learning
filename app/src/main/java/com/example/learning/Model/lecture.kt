package com.example.learning.Model

import java.io.Serializable

class lecture:Serializable {
    var id:String? =""
    var name:String?=""
    var description:String?=""
    var video:String?=""
    var file:String?=""
    constructor()
    constructor(id:String,name:String,description:String,video:String,file:String){
        this.id=id
        this.name=name
        this.description=description
        this.video=video
        this.file=file
    }
    constructor(id:String,name:String,description:String,video:String){
        this.id=id
        this.name=name
        this.description=description
        this.video=video
    }

}