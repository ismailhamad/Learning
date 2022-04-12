package com.example.learning.Firebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.learning.Model.course
import com.example.learning.Model.users
import com.example.learning.View.Student
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FirebaseSource( val activity: Activity){
     lateinit var db:FirebaseFirestore
     lateinit var auth: FirebaseAuth
     lateinit var  progressDialog:ProgressDialog
      var coursea: course?=null
    lateinit var CourseListMutableLiveData: MutableLiveData<List<course>>
     //Log in to the account
    fun Sign_in(Email:String,Password:String){
        progressDialog =ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
     auth =  Firebase.auth
   auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener {
    if (it.isSuccessful){
        if (auth.currentUser!!.isEmailVerified){
            val i=Intent(activity, Student::class.java)
            activity.startActivity(i)
        }else{
            progressDialog.dismiss()
            Toast.makeText(activity, "Please verify your email address", Toast.LENGTH_SHORT).show()
        }

    }else{
        progressDialog.dismiss()
        Toast.makeText(activity, it.exception!!.message,
            Toast.LENGTH_SHORT).show()
    }

}

    }


    //Sign up for the account
    fun Sign_up(users: users){
        auth =  Firebase.auth
        progressDialog =ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        auth.createUserWithEmailAndPassword(users.email,users.password).addOnCompleteListener { task->
            if (task.isSuccessful) {
                auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                    if (it.isSuccessful){
                        createUser(users)
                        progressDialog.dismiss()
                        Toast.makeText(activity, "Registered successully. please check your email",
                            Toast.LENGTH_SHORT).show()
                    }else{
                        progressDialog.dismiss()
                        Toast.makeText(activity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }


                val user = auth.currentUser
            } else {
                progressDialog.dismiss()
                Toast.makeText(activity, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


//Create a user and put it in the firestore
    fun createUser(users: users){
        db=Firebase.firestore
        db.collection("users").add(users).addOnSuccessListener {

        }

    }

    fun addCourse(course: course){
        db=Firebase.firestore
        db.collection("courses").add(course).addOnSuccessListener {

        }
    }



     fun getCourse(): MutableLiveData<List<course>> {
         db=Firebase.firestore
        val Courselist = ArrayList<course>()
         CourseListMutableLiveData =MutableLiveData()
         db.collection("courses").get().addOnSuccessListener { result ->
             for (document in result){
                val course = document.toObject<course>()
                 Courselist.add(course)
                 CourseListMutableLiveData.postValue(Courselist)
             }


         }

         return CourseListMutableLiveData

   }

}