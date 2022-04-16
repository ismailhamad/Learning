package com.example.learning.Firebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

import com.example.learning.Model.course
import com.example.learning.Model.myCourse
import com.example.learning.Model.users
import com.example.learning.View.Student
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
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
    lateinit var MyCourseListMutableLiveData: MutableLiveData<List<myCourse>>
     var  BuyONot : Boolean = false


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
    fun Sign_up(password:String,users: users){
        auth =  Firebase.auth
        progressDialog =ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        auth.createUserWithEmailAndPassword(users.email,password).addOnCompleteListener { task->
            if (task.isSuccessful) {
                auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                    if (it.isSuccessful){
                        createUser(users(auth.currentUser!!.uid,users.name,users.lastName,users.email,0))
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
        auth =  Firebase.auth
        db.collection("users").document(auth.currentUser!!.uid)
            .set(users).addOnSuccessListener {

        }

    }
//addcousrs by lecture
    fun addCourse(course: course){
        db=Firebase.firestore
        db.collection("courses").document(course.id.toString()).set(course).addOnSuccessListener {

        }
    }


//getAllCourse
     fun getCourse(): MutableLiveData<List<course>> {
         db=Firebase.firestore
        val Courselist = ArrayList<course>()
         CourseListMutableLiveData =MutableLiveData()
         db.collection("courses").get().addOnSuccessListener { result ->
             for (document in result!!){
                val course = document.toObject<course>()
                 Courselist.add(course)
                 CourseListMutableLiveData.postValue(Courselist)
             }


         }

         return CourseListMutableLiveData

   }

    //addMyCourse by student
   suspend fun addMyCourse(myCourse: myCourse){
        db=Firebase.firestore
        auth =  Firebase.auth
        progressDialog =ProgressDialog(activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener { it->
            val users=it.toObject<users>()
                if (users!!.numCourse<5){
                    db.collection("myCourse").add(myCourse).addOnSuccessListener {
                        Toast.makeText(activity, "add successfully", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                    }
                }else{
                    //Alert
                    progressDialog.dismiss()
                    Toast.makeText(activity, "no more course", Toast.LENGTH_SHORT).show()
                }

        }



    }

    fun getMyCourse(): MutableLiveData<List<myCourse>> {
        db=Firebase.firestore
        val Courselist = ArrayList<myCourse>()
        MyCourseListMutableLiveData =MutableLiveData()
        auth =  Firebase.auth
        db.collection("myCourse").addSnapshotListener { result, error ->
            for (document in result!!){
                val course = document.toObject<myCourse>()
                if (auth.currentUser!!.uid==course.idusers){
                    Courselist.add(course)
                    updatenumCourse(Courselist.count())
                    MyCourseListMutableLiveData.postValue(Courselist)
                }

            }
        }

        return MyCourseListMutableLiveData

    }



    fun BuyCourseOrNot(id:String):Boolean{
        db=Firebase.firestore
        auth =  Firebase.auth
        db.collection("myCourse").whereEqualTo("idcourse",id).addSnapshotListener { result, error ->
            for (document in result!!){
                val course = document.toObject<myCourse>()
                 //val usesrs = course.users
                     if (auth.currentUser!!.uid==course.idusers){
                        BuyONot = true
                         Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show()
                    }else{
                         BuyONot = false
                         Toast.makeText(activity, "no", Toast.LENGTH_SHORT).show()
                     }

//                for (user in usesrs!!){
//                    val user =user as HashMap<String,users>
//                    if (auth.currentUser!!.uid==user.get("id").toString()){
//                        BuyONot = true
//                    }
//                }


            }
        }

     return BuyONot

    }




    // Atomically add a new region to the "users" array field.
   suspend fun updateUsers(idCourse:String,users: users){
        val users = hashMapOf(
            "id" to users.id,
            "name" to users.name,
            "lastName" to users.lastName,
            "email" to users.email
        ) as Map<String, Any>

        val washingtonRef = db.collection("courses").document(idCourse)

// Atomically add a new region to the "users" array field.
        washingtonRef.update("users", FieldValue.arrayUnion(users)).addOnSuccessListener {


        }
    }


    //Atomically add a new region to the "numCourse" array field.
    fun updatenumCourse(numberr:Int){

            val washingtonRef = db.collection("users").document(auth.currentUser!!.uid)

// Atomically add a new region to the "users" array field.
            washingtonRef.update("numCourse",numberr).addOnSuccessListener {


            }
        }

    }


