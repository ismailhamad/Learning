package com.example.learning.Model

import java.io.Serializable

 class Chat:Serializable{
     var senderId:String = ""
     var receiverId:ArrayList<String>?=null
     var message:String = ""
     var idcourse:String = ""
constructor()
     constructor(senderId:String,receiverId:ArrayList<String>?, message:String,idcourse:String){
         this.senderId=senderId
         this.receiverId=receiverId
         this.message=message
         this.idcourse = idcourse
     }
}