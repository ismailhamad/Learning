package com.example.learning.Model

class myCourse {
    var idcourse:String? =""
    var name:String?=""
    var description:String?=""
    var image:String?=""
    var idusers:String?=""
    var idlecture:String?=""

    constructor(){

    }
    constructor(id:String?,name:String?,description:String?,image:String?,idusers:String?,idlecture:String?){
        this.idcourse=id
        this.name=name
        this.description=description
        this.image=image
        this.idusers=idusers
        this.idlecture=idlecture
    }

}