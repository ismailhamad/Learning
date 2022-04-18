package com.example.learning.Model

import java.io.Serializable

class Assignment: Serializable {
    var id:String? =""
    var name:String?=""
    var description:String?=""
    var file:String?=""
    constructor()
    constructor(id:String,name:String,description:String,file:String){
        this.id = id
        this.name = name
        this.description = description
        this.file = file
    }

    fun getAssignmentHashMap(): HashMap<String, Any?> {
        val data = hashMapOf<String, Any?>(
            "id" to id,
            "name" to name,
            "description" to description,
            "file" to file
        )
        return data
    }

}