package com.example.learning.Model

import java.io.Serializable

class lecture:Serializable {
    var id:String? =""
    var name:String?=""
    var description:String?=""
    var idAssignment:String?=""
    var time:Long?=0

    var seeLecture = true
    var video:String?=""
    var file:String?=""
    constructor()
    constructor(id:String,name:String,description:String,idAssignment:String,time:Long,seeLecture:Boolean,video:String,file:String){
        this.id=id
        this.name=name
        this.description=description
        this.idAssignment=idAssignment
        this.time=time
        this.seeLecture=seeLecture
        this.video=video
        this.file=file
    }
    constructor(id:String,name:String,description:String,idAssignment:String,time:Long,seeLecture:Boolean,video:String){
        this.id=id
        this.name=name
        this.description=description
        this.idAssignment=idAssignment
        this.time=time
        this.seeLecture=seeLecture
        this.video=video
    }
    constructor(id:String,name:String,description:String,idAssignment:String,time:Long,seeLecture:Boolean){
        this.id=id
        this.name=name
        this.description=description
        this.idAssignment=idAssignment
        this.time=time
        this.seeLecture=seeLecture
    }

    fun getlectureHashMap(): HashMap<String, Any?> {
        val data = hashMapOf<String, Any?>(
            "id" to id,
            "name" to name,
            "description" to description,
            "idAssignment" to idAssignment,
            "time" to time,
            "seeLecture" to seeLecture,
            "video" to video,
            "file" to file
        )
        return data
    }

}