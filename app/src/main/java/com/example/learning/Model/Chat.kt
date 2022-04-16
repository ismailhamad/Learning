package com.example.learning.Model

import java.io.Serializable

 class Chat:Serializable{
     var senderId:String = ""
     var receiverId:ArrayList<String>?=null
     var message:String = ""
constructor()
     constructor(senderId:String,receiverId:ArrayList<String>?, message:String){
         this.senderId=senderId
         this.receiverId=receiverId
         this.message=message
     }
}