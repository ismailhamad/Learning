package com.example.learning.Model

import java.io.Serializable

 class Chat:Serializable{
     var id :String = ""
     var senderId:String = ""
     var receiverId:ArrayList<String>?=null
     var message:String = ""
     var idcourse:String = ""
constructor()
     constructor(id:String,senderId:String,receiverId:ArrayList<String>?, message:String,idcourse:String){
         this.id = id
         this.senderId=senderId
         this.receiverId=receiverId
         this.message=message
         this.idcourse = idcourse
     }

     fun getMessageHashMap(): HashMap<String, Any?> {
         val data = hashMapOf<String, Any?>(
             "id" to id,
             "senderId" to senderId,
             "receiverId" to receiverId,
             "message" to message,
             "idcourse" to idcourse
         )
         return data
     }
}