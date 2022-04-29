package com.example.learning.Model

import java.io.Serializable

 class Chat:Serializable{
     var id :String = ""
     var senderId:String = ""
     var receiverId:String?= ""
     var message:String? = ""
     var idcourse:String = ""
     var time:Long?=0
     var image:String?=""
constructor()
     constructor(id:String,senderId:String,receiverId:String?, message:String?,idcourse:String,time:Long,image:String?){
         this.id = id
         this.senderId=senderId
         this.receiverId=receiverId
         this.message=message
         this.idcourse = idcourse
         this.time = time
         this.image = image
     }

     fun getMessageHashMap(): HashMap<String, Any?> {
         val data = hashMapOf<String, Any?>(
             "id" to id,
             "senderId" to senderId,
             "receiverId" to receiverId,
             "message" to message,
             "idcourse" to idcourse,
             "time" to time,
             "image" to image
         )
         return data
     }
}